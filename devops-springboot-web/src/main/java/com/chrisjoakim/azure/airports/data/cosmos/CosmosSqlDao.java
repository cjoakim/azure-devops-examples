package com.chrisjoakim.azure.airports.data.cosmos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import com.chrisjoakim.azure.airports.ApplicationConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.azure.cosmosdb.ConnectionPolicy;
import com.microsoft.azure.cosmosdb.ConsistencyLevel;
import com.microsoft.azure.cosmosdb.Document;
import com.microsoft.azure.cosmosdb.FeedOptions;
import com.microsoft.azure.cosmosdb.FeedResponse;
import com.microsoft.azure.cosmosdb.SqlParameter;
import com.microsoft.azure.cosmosdb.SqlParameterCollection;
import com.microsoft.azure.cosmosdb.SqlQuerySpec;
import com.microsoft.azure.cosmosdb.rx.AsyncDocumentClient;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Instances of this class interact with CosmosDB/SQL via the native SDK, and not Spring Data.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */
@Component
public class CosmosSqlDao {

	private final static ExecutorService executorService = Executors.newFixedThreadPool(100);
	private final static Scheduler scheduler = Schedulers.from(executorService);
	
	Logger logger = LoggerFactory.getLogger(CosmosSqlDao.class);
	
	
	@Autowired
	private ApplicationConfig appConfig;
	
	private AsyncDocumentClient client;
	private String dbName;
	
	public CosmosSqlDao() {
		
		super();
		logger.warn("constructor");
	}
	
	@PostConstruct
	public void init() {
		
		logger.warn("init; appConfig: " + appConfig);
		
		dbName = appConfig.getCosmosSqlDbname();
		logger.warn("init, dbName: " + dbName);
		
		client = new AsyncDocumentClient.Builder()
                .withServiceEndpoint(appConfig.getCosmosSqlUri())
                .withMasterKeyOrResourceToken(appConfig.getCosmosSqlKey())
                .withConnectionPolicy(ConnectionPolicy.GetDefault())
                .withConsistencyLevel(ConsistencyLevel.Eventual)
                .build();
		
		logger.warn("init; client.getReadEndpoint: " + client.getReadEndpoint());
	}
	
	public ArrayList<Document> queryAirportsOrig(String collName, String pk) {

		ArrayList<Document> resultDocuments = new ArrayList<Document>();
		
		int requestPageSize = 100;
        FeedOptions feedOptions = new FeedOptions();
        feedOptions.setMaxItemCount(requestPageSize);
        
        String sql = "SELECT * FROM c where c.pk = @pk";
        SqlParameterCollection params = new SqlParameterCollection();
        params.add(new SqlParameter("@pk", pk));
        SqlQuerySpec querySpec = new SqlQuerySpec(sql, params);
       
		logger.warn("queryAirports; feedOptions: " + feedOptions);
		logger.warn("queryAirports; querySpec:   " + querySpec);

        Observable<FeedResponse<Document>> queryObservable = 
        	client.queryDocuments(getCollectionLink(collName), querySpec, feedOptions);
       
        queryObservable.subscribeOn(scheduler).forEach(page -> {
            for (@SuppressWarnings("unused") Document doc : page.getResults()) {
            	resultDocuments.add(doc);
            	logger.warn("doc: " + doc);
            }
        });
        return resultDocuments;
	}
	
	public List<Document> queryCollByPk(String collName, String pk) {

		ArrayList<Document> resultDocuments = new ArrayList<Document>();
		String collLink = getCollectionLink(collName);
		int requestPageSize = 100;
        FeedOptions feedOptions = new FeedOptions();
        feedOptions.setMaxItemCount(requestPageSize);
        
        String sql = "SELECT * FROM c where c.pk = @pk";
        SqlParameterCollection params = new SqlParameterCollection();
        params.add(new SqlParameter("@pk", pk));
        SqlQuerySpec querySpec = new SqlQuerySpec(sql, params);
       
		logger.warn("queryAirports; feedOptions: " + feedOptions);
		logger.warn("queryAirports; querySpec:   " + querySpec);

		Observable<FeedResponse<Document>> observable = 
			client.queryDocuments(collLink, querySpec, feedOptions);
		
		Iterator<FeedResponse<Document>> it = observable.toBlocking().getIterator();
 
		while (it.hasNext()) {
            FeedResponse<Document> page = it.next();
            List<Document> results = page.getResults();
            for (Object doc : results) {
            	resultDocuments.add((Document) doc);
            }
		}
		return resultDocuments;
	}
	
	public List<Document> queryByLocation(String collName, double longitude, double latitude, double meters) {

		ArrayList<Document> resultDocuments = new ArrayList<Document>();
		String collLink = getCollectionLink(collName);
		int requestPageSize = 100;
        FeedOptions feedOptions = new FeedOptions();
        feedOptions.setMaxItemCount(requestPageSize);
        
        String sql = "SELECT * FROM c where ST_DISTANCE(c.location, {'type': 'Point', 'coordinates':[@longitude,@latitude]}) <= @meters";
        SqlParameterCollection params = new SqlParameterCollection();
        params.add(new SqlParameter("@longitude", longitude));
        params.add(new SqlParameter("@latitude", latitude));
        params.add(new SqlParameter("@meters", meters));
        SqlQuerySpec querySpec = new SqlQuerySpec(sql, params);
       
		logger.warn("queryAirports; feedOptions: " + feedOptions);
		logger.warn("queryAirports; querySpec:   " + querySpec);

		Observable<FeedResponse<Document>> observable = 
			client.queryDocuments(collLink, querySpec, feedOptions);
		
		Iterator<FeedResponse<Document>> it = observable.toBlocking().getIterator();
 
		while (it.hasNext()) {
            FeedResponse<Document> page = it.next();
            List<Document> results = page.getResults();
            for (Object doc : results) {
            	resultDocuments.add((Document) doc);
            }
		}
		return resultDocuments;
	}
	
	private String getCollectionLink(String collName) {
		
        return "dbs/" + dbName + "/colls/" + collName;
    }
	
	public void shutdown() {
		
    	logger.warn("shutdown()");
    	executorService.shutdown();

	}
}
