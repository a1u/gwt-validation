package com.google.gwt.validation.client.interfaces;

import java.util.Set;

import com.google.gwt.validation.client.InvalidConstraint;

/**
 * Basic interface for a validator to validate a given class
 * 
 * @author chris
 *
 * @param <T>
 */
public interface IValidator<T> {

	/**
	 * validate all constraints on object
	 *
	 * @param object object to validate
	 * @param groups group name(s) targeted for validation (default to <code>default</code>)
	 * @return invalid constrains or an empty Set if none
	 * @throws IllegalArgumentException e if object is null
	 */
	public Set<InvalidConstraint<T>> validate(T object, String... groups);
	
	/**
	 * validate all constraints on <code>propertyName</code> property of object
	 *
	 * @param object object to validate
	 * @param propertyName property to validate
	 * @param groups group name(s) targeted for validation (default to <code>default</code>)
	 * @return invalid constrains or an empty Set if none
	 * @throws IllegalArgumentException e if object is null
	 */
	public Set<InvalidConstraint<T>> validateProperty(T object,String propertyName,String... groups);
	
	/**
	 * validate all constraints on <code>propertyName</code> property
	 * if the property value is <code>value</code>
	 *
	 * TODO express limitations of InvalidConstraint in this case
	 *
	 * @param propertyName property to validate
	 * @param value property value to validate
	 * @param groups group name(s) targeted for validation (default to <code>default</code>)
	 * @return invalid constrains or an empty Set if none
	 */
	//public Set<InvalidConstraint<T>> validateValue(String propertyName,Object value,String... groups);
}
