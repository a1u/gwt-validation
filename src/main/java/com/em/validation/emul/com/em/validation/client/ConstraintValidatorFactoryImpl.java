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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ValidationException;
import com.em.validation.client.reflector.IReflectorFactory;
import com.google.gwt.core.client.GWT;

public enum ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {
	
	INSTANCE;
	
	private ConstraintValidatorFactory factory = null;
	
	private ConstraintValidatorFactoryImpl(){
		this.factory = (ConstraintValidatorFactory)GWT.create(ConstraintValidatorFactory.class);
	}

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		if(this.factory == null) {
			throw new ValidationException("ConstraintValidatorFactory was not generated.");
		}
		return this.factory.getInstance(key);
	}
}
