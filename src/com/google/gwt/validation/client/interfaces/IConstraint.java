package com.google.gwt.validation.client.interfaces;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Define the logic to validate a given constraint
 * <br/>
 * (Taken from JSR-303 example implementation in specification document.)
 */
public interface IConstraint<A extends Annotation> {
	
	/**
	 * Initialize the constraint validator.
	 *
	 * @param constraintAnnotation The constraint declaration
	 */
	void initialize(A constraintAnnotation);
	
	/**
	 * Initialize the constraint validator, this is a "hack" for GWT initialization
	 * 
	 * @param propertyMap
	 */
	void initialize(Map<String, String> propertyMap);
	
	/**
 	 * Evaluates the constraint against a value. This method
 	 * must be thread safe.
	 * 
	 * @param value The object to validate
	 * @return false if the value is not valid, true otherwise
	 * @exception IllegalArgumentException The value's type isn't understood
	 * by the constraint validator
	 */
	boolean isValid(Object value);	
}
