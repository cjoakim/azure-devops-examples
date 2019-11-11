package com.chrisjoakim.azure.airports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * The Web Application for this project.
 * 
 * Note that the project contains other @SpringBootApplication CommandLineRunner classes
 * for command-line functions such as for database loading.  See build.sh and the multiple
 * pom_xxx.xml files.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */
@SpringBootApplication
@ComponentScan("com.chrisjoakim.azure")
@Profile("web")
public class WebApplication implements AppConstants {

	private static Logger logger = LoggerFactory.getLogger(WebApplication.class);
	
	public static void main(String[] args) {
		logger.debug("main arg count: " + args.length);
		SpringApplication.run(WebApplication.class, args);
	}
}
