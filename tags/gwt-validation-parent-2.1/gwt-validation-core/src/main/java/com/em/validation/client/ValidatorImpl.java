package com.em.validation.client;

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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class ValidatorImpl implements Validator{
	
	private Validator coreValidator = null;
	private Validator groupSequenceValidator = null;
	
	private ValidatorFactory factory = null;
	
	public ValidatorImpl(ValidatorFactory factory) {
		this.factory = factory;
		
		this.coreValidator = new CoreValidatorImpl(factory);
		this.groupSequenceValidator = new GroupSequenceValidatorImpl(factory);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		if(object == null) {
			throw new IllegalArgumentException("Cannot validate a null object.");
		}
		
		if(groups == null) {
			throw new IllegalArgumentException("The groups list cannot be null.");
		}
		
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validate(object, groups);
		}		
		return this.coreValidator.validate(object, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		if(object == null) {
			throw new IllegalArgumentException("Cannot validate a property on a null object.");
		}
		
		if(propertyName == null) {
			throw new IllegalArgumentException("Cannot validate a property from a null name.");
		}
		
		if(groups == null) {
			throw new IllegalArgumentException("The groups list cannot be null.");
		}

		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validateProperty(object, propertyName, groups);
		}		
		return this.coreValidator.validateProperty(object, propertyName, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		if(beanType == null) {
			throw new IllegalArgumentException("The bean type for validation cannot be null.");
		}
		
		if(propertyName == null) {
			throw new IllegalArgumentException("The property name for validation cannot be null.");
		}
		
		if(groups == null) {
			throw new IllegalArgumentException("The groups list cannot be null.");
		}
		
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(beanType);
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validateValue(beanType, propertyName, value, groups);
		}		
		return this.coreValidator.validateValue(beanType, propertyName, value, groups);

	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return this.coreValidator.getConstraintsForClass(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorImpl.class.equals(type)) {
			return (T) new ValidatorImpl(this.factory);
		} else if (GroupSequenceValidatorImpl.class.equals(type)) {
			return (T) new GroupSequenceValidatorImpl(this.factory);
		} else if (CoreValidatorImpl.class.equals(type)) {
			return (T) new CoreValidatorImpl(this.factory);
		}
		throw new ValidationException("This API does not support unwrapping " + type.getName() + ".");
	}
}
