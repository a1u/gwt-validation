package com.google.gwt.validation.client.test.jsr303;

import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class Author implements IValidatable {
	@NotEmpty(groups="first")
	private String lastName;
	
	@NotEmpty(groups="last")
	private String firstName;

	@Length(maximum=30, groups="last")
	private String company;
	
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

	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
}
