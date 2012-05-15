package com.em.validation.client.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.em.validation.client.model.constraint.ClassLevelConstraint;
import com.em.validation.client.model.generic.ValidatedAtClassLevel;

public class ClassLevelConstraintValidator implements ConstraintValidator<ClassLevelConstraint, ValidatedAtClassLevel> {

	@Override
	public void initialize(ClassLevelConstraint constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(ValidatedAtClassLevel value, ConstraintValidatorContext context) {
		return value.isValid();
	}

}
