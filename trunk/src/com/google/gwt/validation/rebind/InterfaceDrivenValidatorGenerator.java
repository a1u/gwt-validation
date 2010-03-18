/**
 * 
 */
package com.google.gwt.validation.rebind;

import com.google.gwt.validation.client.interfaces.IValidator;

/**
 * @author eldzi
 */
public class InterfaceDrivenValidatorGenerator extends ValidatorGenerator {
	public InterfaceDrivenValidatorGenerator() {
		typeStrategy = new InterfaceTypeNameStrategy(IValidator.class, "Validator");
	}
}
