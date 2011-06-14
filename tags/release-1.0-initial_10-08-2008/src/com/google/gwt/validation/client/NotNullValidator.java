package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@NotNull</code> annotation.
 * 
 * @author chris
 *
 */
public class NotNullValidator implements IConstraint<NotNull> {

	public void initialize(NotNull constraintAnnotation) {
		//nothing
	}

	public void initialize(Map<String, String> propertyMap) {
		//nothing		
	}

	public boolean isValid(Object value) {
		return (value != null);
	}

}
