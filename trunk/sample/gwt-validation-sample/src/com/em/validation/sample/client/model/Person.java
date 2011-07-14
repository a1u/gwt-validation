package com.em.validation.sample.client.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
@Size(min=1)
	@NotNull
	private String lastName;
	
	@NotNull
	@Size(min=3)
	private String firstName;
	
	//@NotNull
	//@Valid
	private Address address;
	  
	public String getLastName() { return this.lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	  
	public String getFirstName() { return this.firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
	  
	@Pattern(regexp="(.*), (.*)")
	public String getFullName() {
	  return this.lastName + ", " + this.firstName;
	}
	  
}
