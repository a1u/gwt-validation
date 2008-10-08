package com.google.gwt.validation.client.test.jsr303;

import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

@ZipCodeCityCoherenceChecker
public class Address2 implements IValidatable {
	@NotNull 
	@Length(maximum=30)
	private String addressline1;
	
	@Length(maximum=30)
	private String addressline2;
	
	@Length(maximum=11)
	private String zipCode;
	
	@NotNull
	@Valid
	private Country country;
	
	private String city;
	
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Length(maximum=30) 
	@NotNull
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}	
}
