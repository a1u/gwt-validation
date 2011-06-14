package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * 
 * Validator that implements the <code>@Length</code> annotation.
 * 
 * @author chris
 *
 */
public class LengthValidator implements IConstraint<Length> {

	private int minimum;
	private int maximum;
	
	public void initialize(Length constraintAnnotation) {
		this.minimum = constraintAnnotation.minimum();
		this.maximum = constraintAnnotation.maximum();
	}

	public void initialize(Map<String, String> propertyMap) {
	
		/*
		 * !!!!
		 * Notice that these keys are exactly the same as the method names on the annotation
		 * !!!!
		 */
		
		
		this.minimum = Integer.parseInt(propertyMap.get("minimum"));
		this.maximum = Integer.parseInt(propertyMap.get("maximum"));
		
	}
	
	public boolean isValid(Object value) {
		if(value == null) return true;
		
		boolean valid = false;
		
		int size = -1;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			size = ((String)value).trim().length();
		}

		if(size >= 0 && this.maximum >= size && this.minimum <= size) valid = true;
		
		return valid;
	}



}
