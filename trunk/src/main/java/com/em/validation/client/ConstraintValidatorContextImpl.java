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

import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

public class ConstraintValidatorContextImpl implements ConstraintValidatorContext {

	private ConstraintDescriptor<?> descriptor = null;
	
	private boolean disableDefault = false;
	
	public ConstraintValidatorContextImpl(ConstraintDescriptor<?> descriptor) {
		this.descriptor = descriptor;
	}
	
	@Override
	public void disableDefaultConstraintViolation() {
		this.disableDefault = true;
	}

	@Override
	public String getDefaultConstraintMessageTemplate() {
		String templateString = "";
		if(this.disableDefault) {
			
		} else {
			if(this.descriptor.getAttributes().containsKey("message")) {
				templateString = (String)descriptor.getAttributes().get("message");
			} else {
				throw new ConstraintDefinitionException("An @Constraint annotation must have a message defined.");
			}
		}		
		return templateString;
	}

	@Override
	public ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate) {
		ConstraintViolationBuilderImpl builder = new ConstraintViolationBuilderImpl();
				
		
		return builder;
	}

}
