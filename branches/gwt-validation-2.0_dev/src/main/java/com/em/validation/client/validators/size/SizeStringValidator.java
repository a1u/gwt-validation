package com.em.validation.client.validators.size;

import javax.validation.ConstraintValidatorContext;

public class SizeStringValidator extends SizeValdiator<String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return true;
		if(value.length() > this.max) return false;
		if(value.length() < this.min) return false;
		return true;
	}

}
