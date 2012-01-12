package com.em.validation.client.model.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.em.validation.client.model.composed.ComposedConstraint;
import com.em.validation.client.model.validator.ClassLevelConstraintValidator;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClassLevelConstraintValidator.class)
@Documented
public @interface ClassLevelConstraint {

	String message() default "{com.em.validation.client.model.constraint.ClassLevelConstraint";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		ComposedConstraint[] value();
	}
	
}
