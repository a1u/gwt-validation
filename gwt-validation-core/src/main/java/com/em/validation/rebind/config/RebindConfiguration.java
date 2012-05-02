package com.em.validation.rebind.config;

import java.util.HashSet;
import java.util.Set;

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

/**
 * Singleton for handling all of the rebind configuration issues.  This class was created because of a suggestion by a user, Niels, who was the
 * first to have a problem integrating with Hibernate.  The need for this class and the configuration it supports grew out of there.
 * 
 * @author chris
 * @see ClassScanner
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
	 * Property string for included model packages.  Can be a single one, or a comma separated list.  
	 */
	private String includedModelPackagesPropertyString = "gwt.validation.included.ModelPackages";
	
	/**
	 * Regular expression representing the classes that implement {@link Validator} to be excluded.
	 */
	private String excludedValidatorClasses = null;
	
	/**
	 * Regular expression representing the annotated model classes to be excluded.
	 */
	private String excludedModelClasses = null;
	
	/**
	 * Set of all the model package urls.
	 * 
	 */
	private Set<String> includedModelPackages = null;
	
	/**
	 * Default constructor that populates the properties of the configuration object
	 * 
	 */
	private RebindConfiguration() {
		String exValidClasses = System.getProperty(this.excludedValidatorClassesPropertyString);
		String exModelClasses = System.getProperty(this.excludedModelClassesPropertyString);
		String inModelClasses = System.getProperty(this.includedModelPackagesPropertyString);
		
		//this could just as easily be != null, null pointer protection either way, this normalizes it by creating a sharp
		//divide between a string and a null option
		if(exValidClasses == null || exValidClasses.isEmpty()) exValidClasses = null;
		if(exModelClasses == null || exModelClasses.isEmpty()) exModelClasses = null;
		if(inModelClasses == null || inModelClasses.isEmpty()) inModelClasses = null;
		
		this.excludedValidatorClasses = exValidClasses;
		this.excludedModelClasses = exModelClasses;		
		
		//if there are model packages listed, split, and trim each package name
		if(inModelClasses != null) {
			String[] packages = inModelClasses.split(",");
			this.includedModelPackages = new HashSet<String>();
			for(String p : packages) {
				this.includedModelPackages.add(p.trim());
			}
			
			//add packages that must be included that are local to gwt-validation and javax.validation
			this.includedModelPackages.add("javax.validation.constraints");
			this.includedModelPackages.add("com.em.validation.client.constraints");
		}
			
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
	
	/**
	 * An array of the model packages that should be included.
	 * 
	 * @return
	 * @see ClassScanner
	 */
	public Set<String> includedModelPackages() {
		return this.includedModelPackages;
	}
}
