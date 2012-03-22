package com.em.validation.rebind.config;

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

import javax.validation.Validator;

import com.em.validation.rebind.scan.ClassScanner;

/**
 * Singleton for handling all of the rebind configuration issues.  This class was created because of a suggestion by a user, Niels, who was the
 * first to have a problem integrating with Hibernate.  The need for this class and the configuration it supports grew out of there.
 * 
 * @author chris
 *
 */
public enum RebindConfiguration {

	/**
	 * Singleton instance
	 */
	INSTANCE;
	
	/**
	 * Property string for excluded validator classes
	 */
	private String excludedValidatorClassesPropertyString = "gwt.validation.excluded.ValidatorClassesRegexp";
	
	/**
	 * Property string for excluded model classes 
	 */
	private String excludedModelClassesPropertyString = "gwt.validation.excluded.ModelClassesRegexp";
	
	/**
	 * Regular expression representing the classes that implement {@link Validator} to be excluded.
	 */
	private String excludedValidatorClasses = null;
	
	/**
	 * Regular expression representing the annotated model classes to be excluded.
	 */
	private String excludedModelClasses = null;
	
	/**
	 * Default constructor that populates the properties of the configuration object
	 * 
	 */
	private RebindConfiguration() {
		String exValidClasses = System.getProperty(this.excludedValidatorClassesPropertyString);
		String exModelClasses = System.getProperty(this.excludedModelClassesPropertyString);
		
		//this could just as easily be != null, null pointer protection either way, this normalizes it by creating a sharp
		//divide between a string and a null option
		if(exValidClasses == null || exValidClasses.isEmpty()) exValidClasses = null;
		if(exModelClasses == null || exModelClasses.isEmpty()) exModelClasses = null;
		
		this.excludedValidatorClasses = exValidClasses;
		this.excludedModelClasses = exModelClasses;		
	}
	
	/**
	 * Returns a string containing a regular expression representing the classes that implement {@link Validator} to be excluded.
	 * 
	 * @return
	 * @see ClassScanner
	 */
	public String excludedValidatorClassesRegularExpression() {
		return this.excludedValidatorClasses;
	}
	
	/**
	 * Returns a string containing a regular expression representing the annotated model classes to be excluded.
	 * 
	 * @return
	 * @see ClassScanner
	 */
	public String excludedModelClassesRegularExpression() {
		return this.excludedModelClasses;
	}
	
}
