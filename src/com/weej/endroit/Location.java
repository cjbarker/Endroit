package com.weej.endroit;

/**
 * Class for representing locations within the United States.
 * 
 * @author CJ Barker
 */
public class Location {

	// member vars
	private Person person;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String county;
	private String areaCode;
	private String prefixSuffix; 
	
	public Location() {}
	
	public Location(String city, String state) {
		this(city, state, null);
	}
	
	public Location(String city, String state, String county) {
		this.city = city;
		this.state = state;
		this.county = county;
	}
	
	@Override
	public String toString() {
		StringBuffer msg = new StringBuffer();
		
		if (person != null) {
			msg.append(person.getFullName()+"\n");
		}
		
		if (address != null && city != null && state != null && zipCode != null) {
			msg.append(address + "\n" + city + ", "+ state + " " + zipCode);
		}
		else if (address != null && city != null && state != null) {
			msg.append(address + "\n" + city + ", "+ state);
		}
		else if (city != null && state != null) {
			msg.append(city + ", "+ state);
		}
		else if (city != null && county != null) {
			msg.append(city + ", "+ county);
		}
		else if (state != null && county != null) {
			msg.append(state + ", "+county);
		}

		if (msg.length() == 0){
			msg.append("Unknown");
		}
		
		return msg.toString();
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
	
	public void setAreaCode(String a) {
		this.areaCode = a;
	}
	
	public String getAreaCode() {
		return this.areaCode;
	}
	
	public void setPrefixSuffix(String s) {
		this.prefixSuffix = s;
	}
	
	public String getPrefixSuffix() {
		return this.prefixSuffix;
	}
	
	public Person getPerson() {
		return this.person;
	}
	
	public void setPerson(Person p) {
		this.person = p;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	
	public void setZipCode(String z) {
		this.zipCode = z;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String a) {
		this.address = a;
	}
}
