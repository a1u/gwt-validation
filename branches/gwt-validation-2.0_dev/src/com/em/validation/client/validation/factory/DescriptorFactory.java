package com.em.validation.client.validation.factory;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.validation.BeanDescriptorImpl;
import com.em.validation.client.validation.PropertyDescriptorImpl;

public enum DescriptorFactory {
	
	INSTANCE;
	
	private DescriptorFactory() {
		
	}
	
	/**
	 * Return an instance of the BeanDescriptor for the given reflector
	 * 
	 * @param reflector
	 * @return
	 */
	public BeanDescriptor getBeanDescriptor(final IReflector<?> reflector) {
		final class PrivateBeanDescriptorImpl extends BeanDescriptorImpl {
			public PrivateBeanDescriptorImpl() {
				this.backingReflector = reflector;
			}			
		}
		
		BeanDescriptor beanDescriptor = new PrivateBeanDescriptorImpl();

		return beanDescriptor;
	}
	 
	/**
	 * Return an instance of a property descriptor for the given property
	 * 
	 * @param reflector
	 * @return
	 */
	public PropertyDescriptor getPropertyDescriptor(final IReflector<?> reflector, final String name) {
		final class PrivatePropertyDescriptorImpl extends PropertyDescriptorImpl {
			public PrivatePropertyDescriptorImpl() {
				this.backingReflector = reflector;
				this.propertyName = name;
			}
		}
		
		PropertyDescriptor propDescriptor = new PrivatePropertyDescriptorImpl();
		
		return propDescriptor;
	}
	
}
