package com.chrisjoakim.azure.airports.web.controllers.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisjoakim.azure.airports.AppConstants;
import com.chrisjoakim.azure.airports.ApplicationConfig;

/**
 * Spring REST Controller intended for Kubernetes health checks, as it
 * implements the /health/ready and /health/alive GET endpoints.
 * 
 * curl "http://localhost:8080/health/ready"
 * curl "http://localhost:8080/health/alive"
 * curl "http://localhost:8080/health/env"
 *
 * @author Chris Joakim
 * @date   2019/11/26
 */
@RestController
@RequestMapping(path="/health", produces="application/json")
@CrossOrigin(origins="*")
public class HealthProbeController implements AppConstants {

	private static long startEpoch = System.currentTimeMillis();
	private static long aliveRequestCount = 0;
	
	Logger logger = LoggerFactory.getLogger(HealthProbeController.class);

	@Autowired
	protected ApplicationConfig appConfig;

	public HealthProbeController() {
		
		super();
	}

	@GetMapping("/ready")
	public ResponseEntity<Map<String,String>> ready() {
		HashMap<String, String> responseData = new HashMap<String, String>();
		
		try {
			responseData.put("status", "ready");
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/alive")
	public ResponseEntity<Map<String,String>> alive() {
		HashMap<String, String> responseData = new HashMap<String, String>();
		
		try {
			aliveRequestCount++;
			long uptimeMs = System.currentTimeMillis() - startEpoch;
			double uptimeHours = (double) uptimeMs / (double) MS_PER_HOUR;
			Runtime runtime = Runtime.getRuntime();
			
			responseData.put("status", "alive");
			responseData.put("uptimeMs", "" + uptimeMs);
			responseData.put("uptimeHours", "" + uptimeHours);
			responseData.put("aliveRequestCount", "" + aliveRequestCount);
			responseData.put("threadName", Thread.currentThread().getName());
			responseData.put("threadId", "" + Thread.currentThread().getId());
			responseData.put("maxMemory", "" + runtime.maxMemory());
			responseData.put("totalMemory", "" + runtime.totalMemory());
			responseData.put("freeMemory", "" + runtime.freeMemory());
			responseData.put("buildDate", appConfig.getBuildDateString());
			responseData.put("buildUser", appConfig.getBuildUserString());
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/env")
	public ResponseEntity<Map<String, String>> azureEnvVars() {
		HashMap<String, String> responseData = new HashMap<String, String>();

		try {
			Map<String, String> env = System.getenv();
			Iterator iterator = env.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				boolean omit = false;
				if ((key.contains("PASS")) || (key.contains("KEY")) || (key.contains("STRING"))) {
					omit = true;
				}
				if (!omit) {
					responseData.put(key, env.get(key));
				}
			}
			return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
