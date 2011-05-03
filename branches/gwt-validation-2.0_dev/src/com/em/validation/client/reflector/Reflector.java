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
	 * Store the property return types
	 */
	protected Map<String,Class<?>> propertyTypes = new HashMap<String, Class<?>>();
	
	/**
	 * Class
	 * 
	 */
	protected Class<?> targetClass = null;
	
	public Class<?> getTargetClass() {
		return this.targetClass;
	}
	
	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames() {
		return this.properties;
	}
	
	public Class<?> getPropertyType(String name) {
		return this.propertyTypes.get(name);
	}
	
	/**
	 * Direct access to the constraint descriptors, from which we can get constraints
	 * 
	 */
	protected Map<String, Set<ConstraintDescriptor<?>>> constraintDescriptors = new HashMap<String, Set<ConstraintDescriptor<?>>>();
	
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		Set<ConstraintDescriptor<?>> outputSet = new HashSet<ConstraintDescriptor<?>>();
		for(Set<ConstraintDescriptor<?>> partialSet : this.constraintDescriptors.values()) {
			outputSet.addAll(partialSet);
		}
		return outputSet;
	}
	
	/**
	 * Get the constraint descriptors present on the given property
	 * 
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name){
		return this.constraintDescriptors.get(name);
	}
}
