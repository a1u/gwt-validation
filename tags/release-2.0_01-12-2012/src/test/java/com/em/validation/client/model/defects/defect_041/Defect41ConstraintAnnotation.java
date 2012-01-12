package com.em.validation.client.model.defects.defect_041;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint( validatedBy={Defect41Validator.class} )
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Defect41ConstraintAnnotation {

	Defect41Enum myEnum();
	
	boolean optional() default true;

	String message() default "{myErrorMessage}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		Defect41ConstraintAnnotation[] value();
	}
}
