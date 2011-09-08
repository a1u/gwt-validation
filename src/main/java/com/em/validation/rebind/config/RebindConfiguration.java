package com.em.validation.rebind.config;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
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
