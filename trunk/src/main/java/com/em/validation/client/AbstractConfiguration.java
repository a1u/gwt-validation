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
import javax.validation.ValidatorFactory;

public abstract class AbstractConfiguration implements Configuration<AbstractConfiguration> {
	private TraversableResolver traversableResolver = null;
	private MessageInterpolator interpolator = null;
	private ConstraintValidatorFactory constraintValidatorFactory = null;
	
	public AbstractConfiguration() {
		this.traversableResolver = new TraversableResolverImpl();
		this.interpolator = new MessageInterpolatorImpl();
		this.constraintValidatorFactory = ConstraintValidatorFactoryImpl.INSTANCE;
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
		this.constraintValidatorFactory = constraintValidatorFactory;
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
		return this.constraintValidatorFactory;
	}

	@Override
	public ValidatorFactory buildValidatorFactory() {
		ValidatorFactoryImpl.INSTANCE.setConfiguration(this);
		return ValidatorFactoryImpl.INSTANCE;
	}
}
