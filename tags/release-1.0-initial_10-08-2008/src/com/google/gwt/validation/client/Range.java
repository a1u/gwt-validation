package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the value contained by the field or method is between minimum and maximum
 * when validated.
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(RangeValidator.class)
@Retention(RUNTIME)
public @interface Range {

	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.range}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
	
	/**
	 * The minimum value of the field or method
	 * 
	 * @return
	 */
	public int minimum() default 0;
	
	/**
	 * The maximum value of the field or method
	 * 
	 * @return
	 */
	public int maximum() default Integer.MAX_VALUE;
	
}
