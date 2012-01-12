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

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class GroupSequenceValidatorImpl implements Validator{
	
	private Validator coreValidator = null;
	
	private ValidatorFactory factory = null;
	
	public GroupSequenceValidatorImpl(ValidatorFactory factory) {
		this.factory = factory;
		
		this.coreValidator = new CoreValidatorImpl(factory);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		return this.validate(new HashSet<Class<?>>(), object, groups);
	}
	
	private <T> Set<ConstraintViolation<T>> validate(Set<Class<?>> previousGroups, T object, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + object.getClass().getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validate(object, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		return this.validateProperty(new HashSet<Class<?>>(), object, propertyName, groups);
	}

	private <T> Set<ConstraintViolation<T>> validateProperty(Set<Class<?>> previousGroups, T object, String propertyName, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + object.getClass().getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validateProperty(object, propertyName, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		return this.validateValue(new HashSet<Class<?>>(), beanType, propertyName, value, groups);
	}
	
	private <T> Set<ConstraintViolation<T>> validateValue(Set<Class<?>> previousGroups, Class<T> beanType,	String propertyName, Object value, Class<?>... groups) { 
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(beanType);
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + beanType.getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validateValue(beanType, propertyName, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return this.coreValidator.getConstraintsForClass(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(GroupSequenceValidatorImpl.class.equals(type)) {
			return (T) new GroupSequenceValidatorImpl(this.factory);
		}
		throw new ValidationException("This API only supports unwrapping " + GroupSequenceValidatorImpl.class.getName() + " (and not " + type.getName() + ").");
	}

}
