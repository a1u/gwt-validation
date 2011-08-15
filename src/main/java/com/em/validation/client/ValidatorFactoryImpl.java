package com.em.validation.client;

import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public enum ValidatorFactoryImpl implements ValidatorFactory {

	INSTANCE;
	
	private Configuration<?> configuration = null;
	
	private TraversableResolver resolver = null;
	
	private ValidatorContext context = null;
	
	private MessageInterpolator interpolator = null;
	
	private ValidatorFactoryImpl() {
		this.setConfiguration(new ConfigurationImpl());
	}
	
	public void setConfiguration(Configuration<?> configuration) {
		if(configuration == null) return;
		this.configuration = configuration;
		this.resolver = this.configuration.getDefaultTraversableResolver();
		this.interpolator = this.configuration.getDefaultMessageInterpolator();
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
