package com.weej.endroit;

public class Person {
	
	// member var
	private String fullName;
	
	public Person() {}
	
	public Person(String name) {
		this.fullName = name;
	}

	// getters & setters
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String n) {
		this.fullName = n;
	}
}
