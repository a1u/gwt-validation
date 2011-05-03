package com.em.validation.client.reflector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

import com.google.gwt.dev.util.collect.HashSet;

public abstract class Reflector<T> implements IReflector<T> {


	/**
	 * Set of unique property names
	 */
	protected Set<String> properties = new HashSet<String>();
	
	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames() {
		return this.properties;
	}
	
	/**
	 * Direct access to the constraint descriptors, from which we can get constraints
	 * 
	 */
	protected Map<String, Set<ConstraintDescriptor<?>>> constraintDescriptors = new HashMap<String, Set<ConstraintDescriptor<?>>>();
	
	/**
	 * Get the constraint descriptors present on the given property
	 * 
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name){
		return this.constraintDescriptors.get(name);
	}
}
