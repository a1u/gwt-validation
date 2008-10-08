package com.google.gwt.validation.client;


import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Allows more than one <code>GroupSequence</code> to be defined on a class.
 * 
 * @author chris
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface GroupSequences {
	GroupSequence[] value();
}