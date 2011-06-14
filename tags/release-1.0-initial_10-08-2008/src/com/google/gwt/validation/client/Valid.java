package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks that a given field or method needs to be validated on its own.
 * 
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Valid {
	
	
}
