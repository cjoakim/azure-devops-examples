package com.chrisjoakim.azure.airports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract superclass of the several CommandLineRunner classes; provides
 * a standard framework for execution - see the runConsoleApp() method.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */
public abstract class ConsoleApp implements AppConstants {

	private static Logger logger = LoggerFactory.getLogger(ConsoleApp.class);
	
	@Autowired
	protected ApplicationConfig appConfig;
	
	private   String[] clArgs = new String[0];
	private   long startTime = -1;
	private   long terminateSleepMs = 3000;
	
	
	public ConsoleApp() {
		
		super();
	}
	
	protected void initialize(String[] args) {

		startTime = System.currentTimeMillis();
		
		if (args != null) {
			clArgs = args;
	        for (int i = 0; i < clArgs.length; ++i) {
	        	logger.warn("initialize arg: " + i + " -> " + clArgs[i]);
	        }   
		}
		else {
        	logger.warn("initialize - null");
		}
	}

	protected String[] getClArgs() {
		
		return clArgs;
	}

	protected long getStartTime() {
		
		return startTime;
	}

	protected long getTerminateSleepMs() {
		
		return terminateSleepMs;
	}

	protected void setTerminateSleepMs(long ms) {
		
		this.terminateSleepMs = Math.abs(ms);
	}
	
	protected void runConsoleApp(String[] args) {
		
        logger.error("==================================================");
        logger.error("runConsoleApp: " + this.getClass().getName());
        logger.error("==================================================");
		try {
			initialize(args);
			execute();    // subclasses must implement execute()
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			terminate();
		}
	}

	
	protected abstract void execute();  // subclasses must implement
	

	protected void terminate() {
		
        try {
        	long   now = System.currentTimeMillis();
        	long   runnerElapsedMs  = now - startTime;
        	double runnerElapsedMin = (double) runnerElapsedMs / (double) MS_PER_MINUTE;
            logger.warn("runner elapsed ms and minutes: " + runnerElapsedMs + " " + runnerElapsedMin);
            logger.warn("terminate, sleeping for " + terminateSleepMs + "ms before exiting ...");
			Thread.sleep(terminateSleepMs);
		}
        catch (InterruptedException e) {
        	e.printStackTrace();
		}
        logger.warn("terminate exiting");
        System.exit(0);
	}
}
