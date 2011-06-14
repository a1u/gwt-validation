package com.em.validation.client.validators;

import java.util.Map;

import javax.validation.ConstraintValidatorContext;

public class SizeMapValidator extends SizeValdiator<Map<?, ?>> {

	@Override
	public boolean isValid(Map<?, ?> value, ConstraintValidatorContext context) {
		if(value == null) return true;
		if(value.size() > this.max) return false;
		if(value.size() < this.min) return false;
		return true;
	}

}
