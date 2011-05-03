package com.em.validation.client.reflector;

import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

public interface IReflector<T> {

	/**
	 * Get the value of the field "name" found in the target object "target".
	 * 
	 * @param target
	 * @param name
	 * @return
	 */
	public Object getValue(String name, T target);
		
	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames();
		
	/**
	 * Given then name of the field or method, return a list of constraint descriptors for that annotation.
	 * 
	 * @param name
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name);
}
