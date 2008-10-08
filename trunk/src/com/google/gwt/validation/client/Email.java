package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the field or method is a string representing
 * an email when validated.
 * <br/><br/>
 * Taken from :  http://www.regular-expressions.info/email.html
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(EmailValidator.class)
@Retention(RUNTIME)
public @interface Email {
	
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.email}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
	
}
