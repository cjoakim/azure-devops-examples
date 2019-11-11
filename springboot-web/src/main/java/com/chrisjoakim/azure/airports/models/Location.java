package com.chrisjoakim.azure.airports.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Instances of this class represent a GeoJSON GPS location.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */

public class Location {

	public static final int LONGITUDE_INDEX = 0;
	public static final int LATITUDE_INDEX  = 1;
	
	private String   type;
	private double[] coordinates = {0, 0};
	
	public Location() {
		
		super();
		this.type = "Point";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public double getLatitude() {
		return this.coordinates[LATITUDE_INDEX];
	}

	@JsonIgnore
	public void setLatitude(double latitude) {
		this.coordinates[LATITUDE_INDEX] = latitude;
	}

	@JsonIgnore
	public double getLongitude() {
		return this.coordinates[LONGITUDE_INDEX];
	}
	
	@JsonIgnore
	public void setLongitude(double longitude) {
		this.coordinates[LONGITUDE_INDEX] = longitude;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
}
