package com.em.validation.client;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

public class ValidatorFactoryImpl implements ValidatorFactory {

	private Configuration<?> configuration = null;
	
	private TraversableResolver resolver = null;
	
	private ValidatorContext context = null;
	
	private MessageInterpolator interpolator = null;
	
	private ConstraintValidatorFactory constraintValidatorFactory = null;
	
	public ValidatorFactoryImpl() {
		this.setConfiguration(new ConfigurationImpl());
	}
	
	public void setConfiguration(Configuration<?> configuration) {
		if(configuration == null) return;
		this.configuration = configuration;
		this.resolver = this.configuration.getDefaultTraversableResolver();
		this.interpolator = this.configuration.getDefaultMessageInterpolator();
		this.constraintValidatorFactory = this.configuration.getDefaultConstraintValidatorFactory();
	}

	@Override
	public Validator getValidator() {
		return new ValidatorImpl(this);
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
		return this.constraintValidatorFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorFactoryImpl.class.equals(type)) {
			return (T) this;
		}
		throw new ValidationException("This API only supports unwrapping " + ValidatorFactoryImpl.class.getName() + " (and not " + type.getName() + ").");
	}
	
	
	
}
