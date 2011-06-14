package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@NotEmpty</code> annotation.
 * 
 * @author chris
 *
 */
public class NotEmptyValidator implements IConstraint<NotEmpty> {

	public void initialize(NotEmpty constraintAnnotation) {
				
	}

	public void initialize(Map<String, String> propertyMap) {
		
	}
	
	public boolean isValid(Object value) {
		//per discussion with JSR-303 specification members
		if(value == null) return false;
		
		boolean valid = false;
		
		int size = -1;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			size = ((String)value).trim().length();
		}

		if(size > 0) valid = true;
		
		return valid;
	}



}
