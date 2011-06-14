package com.em.validation.client;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public enum ValidatorFactoryImpl implements ValidatorFactory {

	INSTANCE;
	
	private TraversableResolver resolver = new TraversableResolverImpl();
	
	private ValidatorContext context = new ValidatorContextImpl();
	
	private MessageInterpolator interpolator = new MessageInterpolatorImpl();
	
	private ValidatorFactoryImpl() {
		
	}

	@Override
	public Validator getValidator() {
		return new ValidatorImpl();
	}

	@Override
	public ValidatorContext usingContext() {
		return this.context;
	}

	@Override
	public MessageInterpolator getMessageInterpolator() {
		return this.interpolator;
	}

	@Override
	public TraversableResolver getTraversableResolver() {
		return this.resolver;
	}

	@Override
	public ConstraintValidatorFactory getConstraintValidatorFactory() {
		return ConstraintValidatorFactoryImpl.INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorFactoryImpl.class.equals(type)) {
			return (T) ValidatorFactoryImpl.INSTANCE;
		}
		throw new ValidationException("This API only supports unwrapping " + ValidatorFactoryImpl.class.getName() + " (and not " + type.getName() + ").");
	}
	
	
	
}
