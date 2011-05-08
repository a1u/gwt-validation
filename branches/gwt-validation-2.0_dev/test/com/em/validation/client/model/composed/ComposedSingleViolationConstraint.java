package com.em.validation.client.model.composed;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NotNull
@Pattern(regexp="[0-9]*")
@Size(min=4, max=6)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComposedSingleViolationConstraint {
	String message() default "{test bad composition}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		ComposedSingleViolationConstraint[] value();
	}
}
