package com.google.gwt.validation.client.interfaces;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

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
