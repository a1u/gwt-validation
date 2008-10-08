package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Email</code> annotation.
 * 
 * @author chris
 *
 */
public class EmailValidator implements IConstraint<Email> {

	public void initialize(Email constraintAnnotation) {

	}

	public void initialize(Map<String, String> propertyMap) {

	}
	
	public boolean isValid(Object value) {
		if(value == null) return true;
		
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";
		
		boolean valid = false;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			valid = ((String)value).matches(emailPattern);
		} else {
			valid = ((Object)value).toString().matches(emailPattern);
		}

		return valid;
	}



}
