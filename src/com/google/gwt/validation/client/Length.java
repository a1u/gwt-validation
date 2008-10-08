package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the field or method will have a length between
 * the minimum and maximum when validated.
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(LengthValidator.class)
@Retention(RUNTIME)
public @interface Length {
	
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.length}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
	

	/**
	 * Minimum length
	 * 
	 * @return
	 */
	public int minimum() default 0;
	
	/**
	 * Maximum length
	 * 
	 * @return
	 */
	public int maximum() default Integer.MAX_VALUE;
	
}
