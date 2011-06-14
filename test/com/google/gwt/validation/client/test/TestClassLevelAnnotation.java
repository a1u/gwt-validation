package com.google.gwt.validation.client.test;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.gwt.validation.client.ConstraintValidator;

@Documented
@Target({METHOD, FIELD, TYPE})
@ConstraintValidator(TestAnnotationConstraint.class)
@Retention(RUNTIME)
public @interface TestClassLevelAnnotation {
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.testannotation}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};
}