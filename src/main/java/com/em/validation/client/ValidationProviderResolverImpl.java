package com.em.validation.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

import com.em.validation.client.spi.ValidationProviderImpl;

public class ValidationProviderResolverImpl implements ValidationProviderResolver {

	@Override
	public List<ValidationProvider<?>> getValidationProviders() {
		List<ValidationProvider<?>> validationProviders = new ArrayList<ValidationProvider<?>>();
		validationProviders.add(new ValidationProviderImpl());		
		return validationProviders;
	}

}
