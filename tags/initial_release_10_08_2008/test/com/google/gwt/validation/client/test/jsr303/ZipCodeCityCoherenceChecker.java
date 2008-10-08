package com.google.gwt.validation.client.test.jsr303;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.gwt.validation.client.ConstraintValidator;

@Documented
@Target({TYPE})
@ConstraintValidator(ZipCodeCityCoherenceCheckerValidator.class)
@Retention(RUNTIME)
public @interface ZipCodeCityCoherenceChecker {

	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.zipcodecoherence}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
	
}
