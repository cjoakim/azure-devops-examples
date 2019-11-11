package com.chrisjoakim.azure.airports.web.controllers.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chrisjoakim.azure.airports.ApplicationConfig;

/**
 * Spring Controller for UI with Thymeleaf templates.
 * 
 * @author Chris Joakim
 * @date   2019/11/05
 */
@Controller
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	protected ApplicationConfig appConfig;
	
	private List<String> links = null;
	
	public IndexController() {
		
    	links = Arrays.asList(
    		"/actuator",
    		"/health/ready",
    		"/health/alive",
    		"/health/env",
    		"/cosmossql/airportsbypk/CLT",
    		"/cosmossql/airportsbypk/ATL",
    		"/cosmossql/airportsbycity/Paris",
    		"/cosmossql/airportsbycountry/Belgium",
    		"/cosmossql/airportsbytimezone/-10",
    		"/cosmossql/airportsbylocation/-80.842840/35.499581/10000");
	}

    @GetMapping("/")
    public String index(
            @RequestParam(name="name", required=false, defaultValue="guest") 
			String name, Model model) {
    	
    	logger.debug("/: " + name);
    	setModel(model, name);        
        return "hello"; // view name
    }
    
    @GetMapping("/home")
    public String home(
            @RequestParam(name="name", required=false, defaultValue="guest") 
			String name, Model model) {
    	
    	logger.debug("/home: " + name);
    	logger.debug("/" + name);
    	setModel(model, name);        
        return "hello"; // view name
    }
    
    private void setModel(Model model, String name) {
    
        model.addAttribute("appName",   appConfig.getAppName());
        model.addAttribute("name",      name);
        model.addAttribute("buildDate", appConfig.getBuildDateString());
        model.addAttribute("buildUser", appConfig.getBuildUserString());
        model.addAttribute("links",     links);
    }
}
