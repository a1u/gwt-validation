package com.em.validation.client.metadata.factory;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.BeanDescriptorImpl;
import com.em.validation.client.metadata.PropertyDescriptorImpl;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;

public enum DescriptorFactory {
	
	INSTANCE;
	
	private IReflectorFactory factory = null;
	
	private DescriptorFactory() {
		this.factory = ReflectorFactory.INSTANCE;
	}
	
	/**
	 * Override the default reflector factory.  Useful for testing, multiple modules, or
	 * other special cases.
	 * 
	 * @param factory
	 */
	public void setReflectorFactory(IReflectorFactory factory) {
		this.factory = factory;
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
	public <T> BeanDescriptor getBeanDescriptor(final Class<T> targetClass) {
		return this.getBeanDescriptor(this.factory.getReflector(targetClass));
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
