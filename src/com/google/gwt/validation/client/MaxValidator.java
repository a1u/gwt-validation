package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Max</code> annotation.
 * 
 * @author chris
 *
 */
public class MaxValidator implements IConstraint<Max> {

	private int maximum;
	
	public void initialize(Max constraintAnnotation) {
		this.maximum = constraintAnnotation.maximum();		
	}

	public void initialize(Map<String, String> propertyMap) {
		/*
		 *	Note that the key is the same as the method name on the constraint 
		 */		
		this.maximum = Integer.parseInt(propertyMap.get("maximum"));
	}

	public boolean isValid(Object value) {
		if(value == null) return true;
		
		boolean isvalid = false;
		
		if(value.getClass().toString().equals(Integer.class.toString())) {
			isvalid = ((Integer)value) < this.maximum;
		} else if (value.getClass().toString().equals(Double.class.toString())) {
			isvalid = ((Double)value) < this.maximum;
		} else if (value.getClass().toString().equals(Float.class.toString())) {
			isvalid = ((Float)value) < this.maximum;
		} else if (value.getClass().toString().equals(Long.class.toString())) {
			isvalid = ((Long)value) < this.maximum;
		}		
		
		return isvalid;
	}

}
