package com.chrisjoakim.azure.airports;

import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import com.chrisjoakim.azure.airports.data.cosmos.AirportRepository;
import com.chrisjoakim.azure.airports.data.cosmos.CosmosSqlDao;
import com.chrisjoakim.azure.airports.models.Airport;
import com.chrisjoakim.io.CsvReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.microsoft.azure.cosmosdb.Document;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Console app for CosmosDB/SQL functions.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */
@SpringBootApplication
@ComponentScan("com.chrisjoakim.azure")
@Profile("cosmossql")
public class CosmosSqlApp extends ConsoleApp implements AppConstants, CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(CosmosSqlApp.class);
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private CosmosSqlDao sqlDao;
	
	public static void main(String[] args) {   
		logger.debug("main arg count: " + args.length);
		SpringApplication.run(CosmosSqlApp.class, args);
	}

	@Override
    public void run(String[] args) {
		runConsoleApp(args);
	}
	
	protected void execute() {
		
		if (getClArgs().length > 1) {
        	logger.warn("appConfig.getAppName():         " + appConfig.getAppName());
        	logger.warn("appConfig.getCosmosSqlUri():    " + appConfig.getCosmosSqlUri());
        	logger.warn("appConfig.getCosmosSqlDbname(): " + appConfig.getCosmosSqlDbname());
//        	logger.warn("appConfig.getCosmosSqlKey():    " + appConfig.getCosmosSqlKey());
//        	logger.warn("appConfig.getUser():            " + appConfig.getUser());
//        	logger.warn("appConfig.getAirportsCsvFile(): " + appConfig.getAirportsCsvFile());
		}
		
		for (int i = 0; i < getClArgs().length; i++) {
			String arg = getClArgs()[i];
			
			// The next few functions interact with CosmosDB via the Spring Data class 'ReactiveCosmosRepository'
			if (arg.equalsIgnoreCase("--load-airports")) {
				loadAirports();
				return;
			}
			if (arg.equalsIgnoreCase("--delete-airports")) {
				deleteAirports();
				return;
			}
			if (arg.equalsIgnoreCase("--find-by-pk")) {
				String pk = getClArgs()[i + 1];
				findByPk(pk);
				return;
			}
			if (arg.equalsIgnoreCase("--find-id-and-by-pk")) {
				String id = getClArgs()[i + 1];
				String pk = getClArgs()[i + 2];
				findByIdAndPk(id, pk);
				return;
			}

			// The next few functions query CosmosDB via class CosmosSqlDao and the CosmosDB SDK; not Spring Data
			if (arg.equalsIgnoreCase("--query-coll-by-pk")) {
				String collName = getClArgs()[i + 1];
				String pk = getClArgs()[i + 2];
				queryCollByPk(collName, pk);
				return;
			}
			if (arg.equalsIgnoreCase("--query-by-location")) {
				String collName  = getClArgs()[i + 1];
				double longitude = Double.parseDouble(getClArgs()[i + 2]);
				double latitude  = Double.parseDouble(getClArgs()[i + 3]);
				double meters    = Double.parseDouble(getClArgs()[i + 4]);
				queryByLocation(collName, longitude, latitude, meters);
				return;
			}
		}
	}

	private void loadAirports() {
		
        logger.warn("loadAirports");

        CsvReader csvReader = new CsvReader();
        // String csvFile = "data/openflights_airports.csv";
        String csvFile = appConfig.getAirportsCsvFile();
        
        try {
			Iterable<CSVRecord> records = csvReader.readWithHeader(csvFile);
			int rowNumber = 0;
			for (CSVRecord record : records) {
				rowNumber++;
				if (rowNumber < 50_000) {
				    //     0      1    2      3       4        5        6        7          8        9        10    11
				    // AirportId,Name,City,Country,IataCode,IcaoCode,Latitude,Longitude,Altitude,TimezoneNum,Dst,TimezoneCode
				    try {
				    	String pk = record.get(4);
				    	String id = UUID.randomUUID().toString();
				    	
						Airport airport = new Airport();
						airport.setId(id); // record.get(0));
						airport.setPk(pk);
						airport.setIataCode(pk);
						airport.setName(record.get(1));
						airport.setCity(record.get(2));
						airport.setCountry(record.get(3));
						airport.setLatitude(Double.parseDouble(record.get(6)));
						airport.setLongitude(Double.parseDouble(record.get(7)));
						airport.setAltitude(Double.parseDouble(record.get(8)));
						airport.setTimezoneNum(record.get(9));
						airport.setTimezoneCode(record.get(11));
						
						ObjectMapper objectMapper = new ObjectMapper();
						String json = objectMapper.writeValueAsString(airport);
						
						logger.warn("airport, row: " + rowNumber + " -> " + pk + " -> " + airport.toString() + " " + airport.isValid());
						logger.warn(json);
						
						if (airport.isValid()) {
							final Mono<Airport> saveMono = airportRepository.save(airport);
							final Airport savedAirport = saveMono.block();
							logger.warn("savedAirport: " + savedAirport.toString());
						}
					}
				    catch (Exception e) {
						logger.error("bad csv data at row number " + rowNumber);
					}
				}
			}
		}
        catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteAirports() {
		
        logger.warn("deleteAirports...");
        this.airportRepository.deleteAll().block();
	}
	
	private void findByPk(String pk) {
		
		try {
			final Flux<Airport> resultsFlux = airportRepository.findByPk(pk);
			Mono<List<Airport>> resultsMono = resultsFlux.collectList();
			List<Airport> resultsList = resultsMono.block();
			logger.debug("results count: " + resultsList.size());
			for (int r = 0; r < resultsList.size(); r++) {
				logger.warn("result: " + r + " -> " + resultsList.get(r).toJsonString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void findByIdAndPk(String id, String pk) {
		
		try {
			final Mono<Airport> findMono = airportRepository.findByIdAndPk(id, pk);
			final Airport airport = findMono.block();
			if (airport != null) {
				logger.warn("airport: " + airport.toJsonString());
			}
			else {
				logger.warn("airport: " + airport.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ===
	
	
	private void queryCollByPk(String collName, String pk) {
		
		try {
			List<Document> resultsList = sqlDao.queryCollByPk(collName, pk);
			logger.debug("resultsList count: " + resultsList.size());
			for (int r = 0; r < resultsList.size(); r++) {
				logger.warn("result: " + r + " -> " + resultsList.get(r).toJson());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void queryByLocation(String collName, double longitude, double latitude, double meters) {
	
		try {
			List<Document> resultsList = sqlDao.queryByLocation(collName, longitude, latitude, meters);
			logger.debug("resultsList count: " + resultsList.size());
			for (int r = 0; r < resultsList.size(); r++) {
				logger.warn("result: " + r + " -> " + resultsList.get(r).toJson());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

