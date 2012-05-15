package com.em.validation.client.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.em.validation.client.model.constraint.CreditCard;

public class CreditCardStringValidator implements ConstraintValidator<CreditCard, String>{

	@Override
	public void initialize(CreditCard constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//this is only for testing, we don't actually care
		return false;
	}

}
