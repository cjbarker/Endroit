package com.weej.endroit;

/**
 * Class for representing locations within the United States.
 * 
 * @author CJ Barker
 */
public class Location {
	
	// member vars
	private String city;
	private String state;
	private String county;
	private String territory;
	
	public Location() {}
	
	public Location(String city, String state) {
		this(city, state, null);
	}
	
	public Location(String city, String state, String county) {
		this(city, state, county, null);
	}
	
	public Location(String city, String state, String county, String territory) {
		this.city = city;
		this.state = state;
		this.county = county;
		this.territory = territory;
	}
	
	@Override
	public String toString() {
		String msg = null;
		if (city != null && state != null) {
			msg = city + ", "+ state;
		}
		else if (city != null && county != null) {
			msg = city + ", "+ county;
		}
		else if (state != null && county != null) {
			msg = state + ", "+county;
		}
		else if (territory != null) {
			msg = territory;
		}
		else {
			msg = "Unknown";
		}
		return msg;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}
}
