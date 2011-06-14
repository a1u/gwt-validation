package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Range</code> annotation.
 * 
 * @author chris
 *
 */
public class RangeValidator implements IConstraint<Range> {

	private int minimum;
	private int maximum;
	
	public void initialize(Range constraintAnnotation) {
		this.maximum = constraintAnnotation.maximum();
		this.minimum = constraintAnnotation.minimum();
		
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
		
		boolean maxValid = false;
		
		if(value.getClass().toString().equals(Integer.class.toString())) {
			maxValid = ((Integer)value) < this.maximum;
		} else if (value.getClass().toString().equals(Double.class.toString())) {
			maxValid = ((Double)value) < this.maximum;
		} else if (value.getClass().toString().equals(Float.class.toString())) {
			maxValid = ((Float)value) < this.maximum;
		} else if (value.getClass().toString().equals(Long.class.toString())) {
			maxValid = ((Long)value) < this.maximum;
		}		
		
		boolean minValid = false;
		
		if(value.getClass().toString().equals(Integer.class.toString())) {
			minValid = ((Integer)value) > this.minimum;
		} else if (value.getClass().toString().equals(Double.class.toString())) {
			minValid = ((Double)value) > this.minimum;
		} else if (value.getClass().toString().equals(Float.class.toString())) {
			minValid = ((Float)value) > this.minimum;
		} else if (value.getClass().toString().equals(Long.class.toString())) {
			minValid = ((Long)value) > this.minimum;
		}		
		
		return (maxValid && minValid);
	}

}
