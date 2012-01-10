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
import javax.validation.ValidatorFactory;

public abstract class AbstractConfiguration implements Configuration<AbstractConfiguration> {
	private TraversableResolver traversableResolver = null;
	private MessageInterpolator interpolator = null;
	private ConstraintValidatorFactory constraintValidatorFactory = null;
	
	public AbstractConfiguration() {
		this.traversableResolver = new TraversableResolverImpl();
		this.interpolator = new MessageInterpolatorImpl();
		this.constraintValidatorFactory = new ConstraintValidatorFactoryImpl();
	}
	
	@Override
	public AbstractConfiguration ignoreXmlConfiguration() {
		return this;
	}

	@Override
	public AbstractConfiguration messageInterpolator(MessageInterpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	@Override
	public AbstractConfiguration traversableResolver(TraversableResolver resolver) {
		this.traversableResolver = resolver;
		return this;
	}

	@Override
	public AbstractConfiguration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
		this.constraintValidatorFactory = constraintValidatorFactory;
		return this;
	}

	@Override
	public AbstractConfiguration addProperty(String name, String value) {
		return this;
	}

	@Override
	public MessageInterpolator getDefaultMessageInterpolator() {
		if(this.interpolator == null) this.interpolator = new MessageInterpolatorImpl();
		return this.interpolator;
	}

	@Override
	public TraversableResolver getDefaultTraversableResolver() {
		if(this.traversableResolver == null) this.traversableResolver = new TraversableResolverImpl();
		return this.traversableResolver;
	}

	@Override
	public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
		return this.constraintValidatorFactory;
	}

	@Override
	public ValidatorFactory buildValidatorFactory() {
		ValidatorFactoryImpl factory = new ValidatorFactoryImpl();
		factory.setConfiguration(this);
		return factory;
	}
}
