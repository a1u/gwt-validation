package com.em.validation.client;

import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorFactory;

public abstract class AbstractConfiguration implements Configuration<AbstractConfiguration> {
	private TraversableResolver traversableResolver = null;
	private MessageInterpolator interpolator = null;
	
	public AbstractConfiguration() {
		this.traversableResolver = new TraversableResolverImpl();
		this.interpolator = new MessageInterpolatorImpl();
	}
	
	@Override
	public AbstractConfiguration ignoreXmlConfiguration() {
		return this;
	}

	@Override
	public AbstractConfiguration messageInterpolator(MessageInterpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	@Override
	public AbstractConfiguration traversableResolver(TraversableResolver resolver) {
		this.traversableResolver = resolver;
		return this;
	}

	@Override
	public AbstractConfiguration constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
		return this;
	}

	@Override
	public AbstractConfiguration addProperty(String name, String value) {
		return this;
	}

	@Override
	public MessageInterpolator getDefaultMessageInterpolator() {
		if(this.interpolator == null) this.interpolator = new MessageInterpolatorImpl();
		return this.interpolator;
	}

	@Override
	public TraversableResolver getDefaultTraversableResolver() {
		if(this.traversableResolver == null) this.traversableResolver = new TraversableResolverImpl();
		return this.traversableResolver;
	}

	@Override
	public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
		return ConstraintValidatorFactoryImpl.INSTANCE;
	}

	@Override
	public ValidatorFactory buildValidatorFactory() {
		return ValidatorFactoryImpl.INSTANCE;
	}
}
