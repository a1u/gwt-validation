package com.google.gwt.validation.client.test.jsr303;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

public class ZipCodeCityCoherenceCheckerValidator implements IConstraint<ZipCodeCityCoherenceChecker> {

	public void initialize(ZipCodeCityCoherenceChecker constraintAnnotation) {
		
	}

	public void initialize(Map<String, String> propertyMap) {
		
	}

	public boolean isValid(Object value) {
		return true;
	}

	
}
