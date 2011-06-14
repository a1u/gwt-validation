package com.em.validation.client;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validator;

public class ValidatorContextImpl implements javax.validation.ValidatorContext {

	@Override
	public javax.validation.ValidatorContext messageInterpolator(MessageInterpolator messageInterpolator) {
		return null;
	}

	@Override
	public javax.validation.ValidatorContext traversableResolver(TraversableResolver traversableResolver) {
		return null;
	}

	@Override
	public javax.validation.ValidatorContext constraintValidatorFactory(ConstraintValidatorFactory factory) {
		return null;
	}

	@Override
	public Validator getValidator() {
		return ValidatorFactoryImpl.INSTANCE.getValidator();
	}

}
