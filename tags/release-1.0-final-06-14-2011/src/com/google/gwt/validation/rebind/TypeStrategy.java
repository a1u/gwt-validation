/**
 * 
 */
package com.google.gwt.validation.rebind;

import com.google.gwt.core.ext.GeneratorContext;

/**
 * @author ladislav.gazo
 */
public interface TypeStrategy {
	
	void setGeneratorContext(GeneratorContext context);
	
	/**
	 * Converts type name comming from GWT.create to required one - usually type
	 * name of the bean class to validate.
	 * 
	 * @param typeName
	 * @return
	 */
	String getBeanTypeName(String typeName);
	
	/**
	 * Converts type name of a bean to required type name resulting in a validator construction.
	 * 
	 * @param typeName
	 * @return
	 */
	String getValidatorTypeName(String typeName);
}
