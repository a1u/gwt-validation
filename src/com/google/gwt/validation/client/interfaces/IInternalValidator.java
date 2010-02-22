/**
 * 
 */
package com.google.gwt.validation.client.interfaces;

import java.util.List;
import java.util.Set;

import com.google.gwt.validation.client.InvalidConstraint;

/**
 * Marker interface for all internal validator implementations.
 * 
 * @author ladislav.gazo
 */
public interface IInternalValidator<T> {
	/**
	 * Actual validation engine class that performs validation internally
	 * 
	 * @param object
	 * @param propertyName
	 * @param groups
	 * @param processedGroups
	 * @param processedObjects
	 * @return
	 */
	Set<InvalidConstraint<T>> performValidation(T object, String propertyName, List<String> groups, Set<String> processedGroups, Set<String> processedObjects);
}
