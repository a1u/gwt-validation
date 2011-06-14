package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Link between an constraint annotation and its constraint validation implementation
 * <br/>
 * A given constraint annotation should be annotated by a @ValidatorClass
 * annotation to refer to its constraint validation implementation 	
 * <br/>
 * (Taken from JSR-303 example implementation in specification document.)
 */
@Documented
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ConstraintValidator {
	Class<? extends IConstraint<?>> value();
}

