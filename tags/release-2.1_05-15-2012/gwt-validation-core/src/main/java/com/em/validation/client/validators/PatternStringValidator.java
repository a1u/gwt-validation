package com.em.validation.client.validators;

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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.em.validation.client.regex.RegexProvider;

public class PatternStringValidator implements ConstraintValidator<Pattern, String> {

	private String pattern = "";
	private Flag[] flags = new Flag[]{};
	
	@Override
	public void initialize(Pattern constraintAnnotation) {
		this.pattern = constraintAnnotation.regexp();
		this.flags = constraintAnnotation.flags();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null) return true;
		
		boolean result = RegexProvider.INSTANCE.matches(this.pattern, value, this.flags);
		
		return result;
	}

}
