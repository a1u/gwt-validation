package com.em.validation.sample.client.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {

	@NotNull
	@Size(min=5)
	private String zip = null;

	@NotNull
	@Size(min=1)
	private String street = null;

	@NotNull
	@Size(min=1)
	private String state = null;

	@NotNull
	@Size(min=1)
	private String city = null;

	@NotNull
	@Valid
	private List<Person> ownerList = null;
	
	@Valid
	@NotNull
	private Person[] owners = new Person[]{};
	
	@Valid
	@NotNull
	private Map<String, Person> personMap = new HashMap<String, Person>();
	
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Person> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<Person> ownerList) {
		this.ownerList = ownerList;
	}

	public Person[] getOwners() {
		return owners;
	}

	public void setOwners(Person[] owners) {
		this.owners = owners;
	}

	public Map<String, Person> getPersonMap() {
		return personMap;
	}

	public void setPersonMap(Map<String, Person> personMap) {
		this.personMap = personMap;
	}

}
