package com.em.validation.client.model.defects.defect_041;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Defect41Validator implements ConstraintValidator<Defect41ConstraintAnnotation, Set<Defect41Enum>> {

	private Defect41Enum dEnum;
	
	@Override
	public void initialize(Defect41ConstraintAnnotation constraintAnnotation) {
		this.dEnum = constraintAnnotation.myEnum();
	}

	@Override
	public boolean isValid(Set<Defect41Enum> value, ConstraintValidatorContext context) {
		if(value == null) return false;
		
		return value.contains(this.dEnum);
	}
	
}
