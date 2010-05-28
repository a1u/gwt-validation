package com.google.gwt.validation.client.common;

import java.util.Map;

public abstract class MinValidatorAbstract {
	protected int minimum;


	public void initialize(Map<String, String> propertyMap) {
		/*
		 *	Note that the key is the same as the method name on the constraint
		 */
		this.minimum = Integer.parseInt(propertyMap.get("value"));
	}

	public boolean isValid(Object value) {
		if(value == null) return true;

		boolean isvalid = false;

		if(value.getClass().toString().equals(Integer.class.toString())) {
			isvalid = ((Integer)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Double.class.toString())) {
			isvalid = ((Double)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Float.class.toString())) {
			isvalid = ((Float)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Long.class.toString())) {
			isvalid = ((Long)value) >= this.minimum;
		}

		return isvalid;
	}


}
