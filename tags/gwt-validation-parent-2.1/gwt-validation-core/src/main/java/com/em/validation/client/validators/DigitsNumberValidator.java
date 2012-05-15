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
import javax.validation.constraints.Digits;

/**
 * Validates the number of integer and or fractional digits in a number
 * 
 * @author chris
 *
 */
public class DigitsNumberValidator implements ConstraintValidator<Digits, Number> {

	private int fraction = 0;
	private int integer = 0;
	
	@Override
	public void initialize(Digits constraintAnnotation) {
		this.fraction = constraintAnnotation.fraction();
		this.integer = constraintAnnotation.integer();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null) return true;
		
		boolean result = true;
		
		//get double value as string
		String valueString = Double.valueOf(value.doubleValue()).toString();
		
		//initialize the digit counts
		int intDigits = 0;
		int frcDigits = 0;
		
		if(valueString.contains(".")) {
			String[] partSplit = valueString.split("\\.");
			intDigits = partSplit[0].length();
			frcDigits = partSplit[1].length();
		} else {
			intDigits = valueString.length();
		}		
		
		//the digits validator specifies the maximum number of digits, going over that will result in 
		//a failing validation
		if(intDigits > this.integer || frcDigits > this.fraction) {
			result = false;
		}
		
		return result;
	}

}
