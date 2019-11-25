package com.chrisjoakim.azure.airports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * Minimal console app for testing purposes.
 * 
 * @author Chris Joakim
 * @date   2019/11/05
 */
@SpringBootApplication
@ComponentScan("com.chrisjoakim.azure")
@Profile("noop")
public class NoOpApp extends ConsoleApp implements CommandLineRunner {

	// Instance variables:
	private static Logger logger = LoggerFactory.getLogger(NoOpApp.class);

	public static void main(String[] args) {
	
		logger.debug("main arg count: " + args.length);  
		SpringApplication.run(NoOpApp.class, args);
	}

	@Override
    public void run(String[] args) {
		
		runConsoleApp(args);
	}
	
	protected void execute() {
		
    	logger.warn("appConfig.getAppName():         " + appConfig.getAppName());
    	logger.warn("appConfig.getCosmosSqlUri():    " + appConfig.getCosmosSqlUri());
    	logger.warn("appConfig.getCosmosSqlKey():    " + appConfig.getCosmosSqlKey());
    	logger.warn("appConfig.getCosmosSqlDbname(): " + appConfig.getCosmosSqlDbname());
    	logger.warn("appConfig.getUser():            " + appConfig.getUser());
    	logger.warn("appConfig.getAirportsCsvFile(): " + appConfig.getAirportsCsvFile());
    	logger.warn("appConfig.getBuildDateString(): " + appConfig.getBuildDateString());
    	logger.warn("appConfig.getBuildUserString(): " + appConfig.getBuildUserString());
	}
}
