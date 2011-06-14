package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Allows the use of more than one pattern on a given field
 * or method.
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Patterns {
	
	public Pattern[] value();
	
}
