package com.em.validation.client.model.composed;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@CyclicalComposedConstraintPart1
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CyclicalComposedConstraintPart2 {
	String message() default "{test bad composition}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		CyclicalComposedConstraintPart2[] value();
	}
}
