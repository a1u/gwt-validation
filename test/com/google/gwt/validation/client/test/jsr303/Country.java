package com.google.gwt.validation.client.test.jsr303;

import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class Country implements IValidatable {
	@NotNull
	private String name;
	@Length(maximum=2) 
	private String ISO2Code;
	@Length(maximum=3) 
	private String ISO3Code;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getISO2Code() {
		return ISO2Code;
	}
	public void setISO2Code(String ISO2Code) {
		this.ISO2Code = ISO2Code;
	}
	public String getISO3Code() {
		return ISO3Code;
	}
	public void setISO3Code(String ISO3Code) {
		this.ISO3Code = ISO3Code;
	}
}