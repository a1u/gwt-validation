package com.google.gwt.validation.client.common;

import java.util.Map;

public abstract class NotNullValidatorAbstract {
	public void initialize(Map<String, String> propertyMap) {
		//nothing		
	}

	public boolean isValid(Object value) {
		return (value != null);
	}

}
