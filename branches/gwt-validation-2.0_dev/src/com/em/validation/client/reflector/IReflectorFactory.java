package com.em.validation.client.reflector;

/**
 * The interface that needs to be implemented so that any class that needs
 * to can reach out and grab a backing reflector for any of the dynamic 
 * operations that they need to complete.
 * 
 * @author chris
 *
 */
public interface IReflectorFactory {

	/**
	 * Get a reflector for the given targetClass
	 * 
	 * @param <T>
	 * @param targetClass
	 * @return
	 */
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass);
	
}
