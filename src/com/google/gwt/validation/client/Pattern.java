package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the field or method matches the given regex pattern
 * when validated.  This regex pattern should be written so that it
 * has the same functionality in Java and JavaScript.
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(PatternValidator.class)
@Retention(RUNTIME)
public @interface Pattern {
	
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.pattern}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
	

	/**
	 * String value of the regex pattern to match against.  This should work
	 * the same in Java and JavaScript or validations may not occur properly.
	 * <br/>
	 * By default it matches anything. 
	 * 
	 * @return
	 */
	public String pattern() default ".*";
	
}
