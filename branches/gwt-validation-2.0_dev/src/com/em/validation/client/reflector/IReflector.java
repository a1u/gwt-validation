package com.em.validation.client.reflector;

import java.lang.annotation.Annotation;
import java.util.Set;

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
	 * Given then name of the field or method, return the string that represents the annotation.
	 * 
	 * @param name
	 * @return
	 */
	public Set<Annotation> getAnnotations(String name);
}
