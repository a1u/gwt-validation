package com.em.validation.client.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.AssertFalse;

public class AssertFalseBooleanValidator implements ConstraintValidator<AssertFalse, Boolean> {

	@Override
	public void initialize(AssertFalse constraintAnnotation) {

	}

	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {
		if(value == null) return true;
		return !value;
	}

}
