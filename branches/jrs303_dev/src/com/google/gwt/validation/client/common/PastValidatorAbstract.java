package com.google.gwt.validation.client.common;

import java.util.Date;
import java.util.Map;

public abstract class PastValidatorAbstract {

	public boolean isValid(Object value) {
		if (value == null)
			return true;

		boolean isvalid = false;

		try {
			Date date = (Date) value;
			isvalid = date.before(new Date());
		} catch (Exception ex) {

		}

		return isvalid;
	}

	public void initialize(Map<String, String> propertyMap) {

	}

}
