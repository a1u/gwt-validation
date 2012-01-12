package com.google.gwt.validation.client;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Assert that a method or field will be true when validated
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(AssertTrueValidator.class)
@Retention(RUNTIME)
public @interface AssertTrue {
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.true}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
}