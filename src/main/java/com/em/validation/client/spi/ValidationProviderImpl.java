package com.em.validation.client.spi;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

import com.em.validation.client.AbstractConfiguration;
import com.em.validation.client.ConfigurationImpl;
import com.em.validation.client.ValidatorFactoryImpl;

public class ValidationProviderImpl implements ValidationProvider<AbstractConfiguration>{

	@Override
	public AbstractConfiguration createSpecializedConfiguration(BootstrapState state) {
		return new ConfigurationImpl();
	}

	@Override
	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		return new ConfigurationImpl();
	}

	@Override
	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return ValidatorFactoryImpl.INSTANCE;
	}

}
