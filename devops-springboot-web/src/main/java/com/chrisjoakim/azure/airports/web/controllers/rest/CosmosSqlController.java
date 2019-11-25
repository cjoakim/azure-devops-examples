package com.chrisjoakim.azure.airports.web.controllers.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisjoakim.azure.airports.AppConstants;
import com.chrisjoakim.azure.airports.data.cosmos.AirportRepository;
import com.chrisjoakim.azure.airports.data.cosmos.CosmosSqlDao;
import com.chrisjoakim.azure.airports.models.Airport;
import com.microsoft.azure.cosmosdb.Document;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring REST Controller returns CosmosDB/SQL Documents.
 * 
 * curl "http://localhost:3001/cosmossql/airportsbypk/CLT"
 * 
 *  
 * @author Chris Joakim
 * @date   2019/11/05
 */
@RestController
@RequestMapping(path="/cosmossql", produces="application/json")
@CrossOrigin(origins="*")
public class CosmosSqlController implements AppConstants {

	private static long startEpoch = System.currentTimeMillis();
	private static long aliveRequestCount = 0;
	
	Logger logger = LoggerFactory.getLogger(CosmosSqlController.class);

	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private CosmosSqlDao sqlDao;
	
	
	public CosmosSqlController() {
		
		super();
	}

	@GetMapping("/airportsbypk/{pk}")
	public ResponseEntity<Map<String,String>> airportsByPk(@PathVariable String pk) {
		
		try {
			logger.debug("airportsByPk: " + pk);
			final Flux<Airport> resultsFlux = airportRepository.findByPk(pk);
			Mono<List<Airport>> resultsMono = resultsFlux.collectList();
			List<Airport> resultsList = resultsMono.block();
			logger.debug("airportsByPk results count: " + resultsList.size());
			return new ResponseEntity(resultsList, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/airportsbycity/{city}")
	public ResponseEntity<Map<String,String>> airportsByCity(@PathVariable String city) {
		
		try {
			logger.debug("airportsByCity: " + city);
			final Flux<Airport> resultsFlux = airportRepository.findByCity(city);
			Mono<List<Airport>> resultsMono = resultsFlux.collectList();
			List<Airport> resultsList = resultsMono.block();
			logger.debug("airportsByCity results count: " + resultsList.size());
			return new ResponseEntity(resultsList, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/airportsbycountry/{country}")
	public ResponseEntity<Map<String,String>> airportsByCountry(@PathVariable String country) {
		
		try {
			logger.debug("airportsByCountry: " + country);
			final Flux<Airport> resultsFlux = airportRepository.findByCountry(country);
			Mono<List<Airport>> resultsMono = resultsFlux.collectList();
			List<Airport> resultsList = resultsMono.block();
			logger.debug("airportsByCountry results count: " + resultsList.size());
			return new ResponseEntity(resultsList, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/airportsbytimezone/{timezoneNum}")
	public ResponseEntity<Map<String,String>> airportsByTimezoneNum(@PathVariable String timezoneNum) {
		
		try {
			logger.debug("airportsByTimezoneNum: " + timezoneNum);
			final Flux<Airport> resultsFlux = airportRepository.findByTimezoneNum(timezoneNum);
			Mono<List<Airport>> resultsMono = resultsFlux.collectList();
			List<Airport> resultsList = resultsMono.block();
			logger.debug("airportsByTimezoneNum results count: " + resultsList.size());
			return new ResponseEntity(resultsList, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/airportsbylocation/{longitude}/{latitude}/{meters}")
	public ResponseEntity<Map<String,String>> airportsByLocation(
			@PathVariable double longitude,
			@PathVariable double latitude,
			@PathVariable double meters) {
		
		try {
			logger.debug("airportsByLocation: " + longitude + ", " + latitude + ", " + meters);
			List<Document> resultsList = sqlDao.queryByLocation("airports", longitude, latitude, meters);
			
			logger.debug("airportsByLocation results count: " + resultsList.size());
			return new ResponseEntity(resultsList, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
