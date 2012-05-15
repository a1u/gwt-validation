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

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validator;

public class ValidatorContextImpl implements javax.validation.ValidatorContext {

	private Validator validator = null;
	
	public ValidatorContextImpl(Validator validator) {
		this.validator = validator;
	}
	
	@Override
	public javax.validation.ValidatorContext messageInterpolator(MessageInterpolator messageInterpolator) {
		return this;
	}

	@Override
	public javax.validation.ValidatorContext traversableResolver(TraversableResolver traversableResolver) {
		return this;
	}

	@Override
	public javax.validation.ValidatorContext constraintValidatorFactory(ConstraintValidatorFactory factory) {
		return this;
	}

	@Override
	public Validator getValidator() {
		return this.validator;
	}

}
