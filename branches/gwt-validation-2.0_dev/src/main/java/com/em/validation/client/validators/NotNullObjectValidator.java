package com.em.validation.client.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class NotNullObjectValidator implements ConstraintValidator<NotNull,Object> {

	@Override
	public void initialize(NotNull arg0) {
				
	}

	@Override
	public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
		return null != arg0;
	}


}
