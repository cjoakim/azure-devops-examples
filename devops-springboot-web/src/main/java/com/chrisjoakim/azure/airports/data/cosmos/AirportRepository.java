package com.chrisjoakim.azure.airports.data.cosmos;

import org.springframework.stereotype.Repository;

import com.chrisjoakim.azure.airports.models.Airport;

import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Instances of this class interact with CosmosDB/SQL via Spring Data, and not the native SDK.
 *  
 * A Flux is a stream which emits 0..n elements.
 * A Mono is a stream which emits 0..1 elements.
 * 
 * See https://www.baeldung.com/reactor-core
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */

@Repository
public interface AirportRepository extends ReactiveCosmosRepository<Airport, String> { 
	
	Flux<Airport> findByPk(String partitionKey);
	
	Mono<Airport> findByIdAndPk(String id, String partitionKey);
	
	Flux<Airport> findByCity(String city);
	
	Flux<Airport> findByCountry(String country);
	
	Flux<Airport> findByTimezoneNum(String timezoneNum);
	
	// See https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.at-query
}

/**
(AzureDiagnostics
| where TimeGenerated >= ago(2h)
| where Category == "QueryRuntimeStatistics"
| distinct activityId_g, querytext_s
) | join kind= inner (
    AzureDiagnostics
   | where TimeGenerated >= ago(2h)
   | where Category == "DataPlaneRequests"
) on $left.activityId_g == $right.activityId_g
| where todouble(requestCharge_s) >= 0

// (AzureDiagnostics | where activityId_g == "97a834e3-9bdf-44c7-bbec-83a82996acf2" | project querytext_s)
*/
