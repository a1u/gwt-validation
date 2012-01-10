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

import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public class ValidatorFactoryImpl implements ValidatorFactory {

	private Configuration<?> configuration = null;
	
	private TraversableResolver resolver = null;
	
	private ValidatorContext context = null;
	
	private MessageInterpolator interpolator = null;
	
	private ConstraintValidatorFactory constraintValidatorFactory = null;
	
	public ValidatorFactoryImpl() {
		this.setConfiguration(new ConfigurationImpl());
	}
	
	public void setConfiguration(Configuration<?> configuration) {
		if(configuration == null) return;
		this.configuration = configuration;
		this.resolver = this.configuration.getDefaultTraversableResolver();
		this.interpolator = this.configuration.getDefaultMessageInterpolator();
		this.constraintValidatorFactory = this.configuration.getDefaultConstraintValidatorFactory();
	}

	@Override
	public Validator getValidator() {
		return new ValidatorImpl(this);
	}

	@Override
	public ValidatorContext usingContext() {
		return this.context;
	}

	@Override
	public MessageInterpolator getMessageInterpolator() {
		return this.interpolator;
	}

	@Override
	public TraversableResolver getTraversableResolver() {
		return this.resolver;
	}

	@Override
	public ConstraintValidatorFactory getConstraintValidatorFactory() {
		return this.constraintValidatorFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorFactoryImpl.class.equals(type)) {
			return (T) this;
		}
		throw new ValidationException("This API only supports unwrapping " + ValidatorFactoryImpl.class.getName() + " (and not " + type.getName() + ").");
	}
	
	
	
}
