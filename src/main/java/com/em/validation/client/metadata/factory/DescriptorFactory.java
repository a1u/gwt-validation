package com.em.validation.client.metadata.factory;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import javax.validation.ValidationException;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.BeanDescriptorImpl;
import com.em.validation.client.metadata.PropertyDescriptorImpl;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;

/**
 * A factory class to construct a bean descriptor when given a reflector.  It will also automatically
 * use the reflector factory if not provided with a reflector instance.
 * 
 * This is a convenience method instead of constructing the BeanDescriptor with the backing reflector directly.
 * 
 * @author chris
 *
 */
public enum DescriptorFactory {
	
	/**
	 * Singleton pattern
	 */
	INSTANCE;
	
	/**
	 * Private instance of the reflection factory
	 * 
	 */
	private IReflectorFactory factory = null;
	
	/**
	 * Default constructor that uses the ReflectorFactory singleton to grab an instance to a reflector factory.  In GWT compiled
	 * mode this factory is replaced through code generation and deferred binding to be the generated factory
	 * 
	 */
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
		if(targetClass == null) {
			throw new ValidationException("Cannot get bean descriptor metatada for a null class.");
		}
		return this.getBeanDescriptor(this.factory.getReflector(targetClass));
	}
	
	/**
	 * Return an instance of the BeanDescriptor for the given reflector
	 * 
	 * @param reflector
	 * @return
	 */
	public BeanDescriptor getBeanDescriptor(final IReflector reflector) {
		BeanDescriptor beanDescriptor = new BeanDescriptorImpl(reflector);
		return beanDescriptor;
	}
	 
	/**
	 * Return an instance of a property descriptor for the given property
	 * 
	 * @param reflector
	 * @return
	 */
	public PropertyDescriptor getPropertyDescriptor(final IReflector reflector, final String name) {
		PropertyDescriptor propDescriptor = new PropertyDescriptorImpl(reflector, name);
		return propDescriptor;
	}
	
}
