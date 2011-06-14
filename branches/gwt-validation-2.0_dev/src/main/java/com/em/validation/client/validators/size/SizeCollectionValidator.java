package com.em.validation.client.validators.size;

import java.util.Collection;

import javax.validation.ConstraintValidatorContext;

public class SizeCollectionValidator extends SizeValdiator<Collection<?>> {

	@Override
	public boolean isValid(Collection<?> value,	ConstraintValidatorContext context) {
		if(value == null) return true;
		if(value.size() > this.max) return false;
		if(value.size() < this.min) return false;
		return true;
	}

}
