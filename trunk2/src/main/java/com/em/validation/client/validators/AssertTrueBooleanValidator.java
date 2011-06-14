package com.em.validation.client.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.AssertTrue;

public class AssertTrueBooleanValidator implements ConstraintValidator<AssertTrue, Boolean> {

	@Override
	public void initialize(AssertTrue constraintAnnotation) {

	}

	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {
		if(value == null) return true;
		return value;
	}

}
