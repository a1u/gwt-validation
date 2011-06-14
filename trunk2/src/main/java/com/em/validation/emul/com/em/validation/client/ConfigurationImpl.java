package com.em.validation.client;

import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorFactory;

public class ConfigurationImpl implements Configuration<ConfigurationImpl> {

	@Override
	public ConfigurationImpl ignoreXmlConfiguration() {
		return this;
	}

	@Override
	public ConfigurationImpl messageInterpolator(MessageInterpolator interpolator) {
		return this;
	}

	@Override
	public ConfigurationImpl traversableResolver(TraversableResolver resolver) {
		return this;
	}

	@Override
	public ConfigurationImpl constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
		return this;
	}

	@Override
	public ConfigurationImpl addProperty(String name, String value) {
		return this;
	}

	@Override
	public MessageInterpolator getDefaultMessageInterpolator() {
		return null;
	}

	@Override
	public TraversableResolver getDefaultTraversableResolver() {
		return null;
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
