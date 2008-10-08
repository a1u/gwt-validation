package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class RecursiveValidationTest implements IValidatable {

	@Valid
	private RecursiveValidationTest rvt = null;

	public RecursiveValidationTest() {

	}
	
	public RecursiveValidationTest getRvt() {
		return rvt;
	}

	public void setRvt(RecursiveValidationTest rvt) {
		this.rvt = rvt;
	}
	
	@NotNull
	private String testString = null;

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
	
	
	
}
