package com.em.validation.client;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import java.io.InputStream;

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
	public ConfigurationImpl addMapping(InputStream stream) {
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
