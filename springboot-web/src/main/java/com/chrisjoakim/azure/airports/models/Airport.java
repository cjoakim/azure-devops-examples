package com.chrisjoakim.azure.airports.models;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;

/**
 * CosmosDB @Document class used by @Repository class AirportRepository.
 * 
 * @author Chris Joakim
 * @date   2019/11/04
 */
@Document(collection="airports")
public class Airport {
	
	@Id
	private String id;
	
	@PartitionKey
	private String pk;
	
	// AirportId,Name,City,Country,IataCode,IcaoCode,Latitude,Longitude,Altitude,TimezoneNum,Dst,TimezoneCode
	private String   iataCode;
	private String   name;
	private String   city;
	private String   country;
	private double   latitude = 200;
	private double   longitude = 200;
	private double   altitude;
	private Location location;
	private String   timezoneNum;
	private String   timezoneCode;

	
	public Airport() {
		
		super();
		this.location = new Location();
	}

	public boolean isValid() {
		
		if ((pk == null || (pk.length() < 3))) {
			return false;
		}
		if ((iataCode == null || (iataCode.length() < 3))) {
			return false;
		}
		if ((name == null || (name.length() < 3))) {
			return false;
		}
		if (Math.abs(latitude) > 180) {
			return false;
		}
		if (Math.abs(longitude) > 180) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		
		return "id: " + this.id + ", pk: " + this.pk + ", name: " + this.name + ", city: " + this.city + ", country: " + this.country +
				", lat: " + this.latitude;
	}

	public String toJsonString() {
		
		try {
			return new ObjectMapper().writeValueAsString(this);
		}
		catch (JsonProcessingException e) {
			return "{}";
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		if (pk == null) {
			return;
		}
		this.pk = pk.trim();
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		if (iataCode == null) {
			return;
		}
		this.iataCode = iataCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			return;
		}
		this.name = name.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
		this.location.setLatitude(latitude);
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
		this.location.setLongitude(longitude);
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getTimezoneNum() {
		return timezoneNum;
	}

	public void setTimezoneNum(String timezoneNum) {
		this.timezoneNum = timezoneNum;
	}

	public String getTimezoneCode() {
		return timezoneCode;
	}

	public void setTimezoneCode(String timezoneCode) {
		this.timezoneCode = timezoneCode;
	}
}
