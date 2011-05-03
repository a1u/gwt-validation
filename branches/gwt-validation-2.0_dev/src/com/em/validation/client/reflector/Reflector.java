package com.em.validation.client.reflector;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	protected Map<String, Set<Annotation>> annotationMap = new HashMap<String, Set<Annotation>>();
	
	public Set<Annotation> getAnnotations(String name){
		return this.annotationMap.get(name);
	}
}
