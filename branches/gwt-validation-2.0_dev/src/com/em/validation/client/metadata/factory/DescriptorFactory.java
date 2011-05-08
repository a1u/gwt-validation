package com.em.validation.client.metadata.factory;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.BeanDescriptorImpl;
import com.em.validation.client.metadata.PropertyDescriptorImpl;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public enum DescriptorFactory {
	
	INSTANCE;
	
	private DescriptorFactory() {
		
	}
	
	/**
	 * Get the bean descriptor from the target object
	 * 
	 * @param targetObject
	 * @return
	 */
	public BeanDescriptor getBeanDescriptor(Object targetObject) {
		return this.getBeanDescriptor(targetObject.getClass());
	}
	
	/**
	 * Get a bean descriptor directly from the target class
	 * 
	 * @param targetClass
	 * @return
	 */
	public BeanDescriptor getBeanDescriptor(final Class<?> targetClass) {
		return this.getBeanDescriptor(ReflectorFactory.INSTANCE.getReflector(targetClass));
	}
	
	/**
	 * Return an instance of the BeanDescriptor for the given reflector
	 * 
	 * @param reflector
	 * @return
	 */
	public BeanDescriptor getBeanDescriptor(final IReflector<?> reflector) {
		BeanDescriptor beanDescriptor = new BeanDescriptorImpl(reflector);
		return beanDescriptor;
	}
	 
	/**
	 * Return an instance of a property descriptor for the given property
	 * 
	 * @param reflector
	 * @return
	 */
	public PropertyDescriptor getPropertyDescriptor(final IReflector<?> reflector, final String name) {
		PropertyDescriptor propDescriptor = new PropertyDescriptorImpl(reflector, name);
		return propDescriptor;
	}
	
}
