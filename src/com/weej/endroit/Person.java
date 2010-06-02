package com.weej.endroit;

public class Person {
	
	// member var
	private String firstName;
	private String lastName;
	
	public Person() {}
	
	public Person(String fName, String lName) {
		this.firstName = fName;
		this.lastName = lName;
	}

	// getters & setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
