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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class ValidatorImpl implements Validator{
	
	private Validator coreValidator = new CoreValidatorImpl();
	private Validator groupSequenceValidator = new GroupSequenceValidatorImpl();
	
	public ValidatorImpl() {		

	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validate(object, groups);
		}		
		return this.coreValidator.validate(object, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validateProperty(object, propertyName, groups);
		}		
		return this.coreValidator.validateProperty(object, propertyName, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(beanType);
		if(reflector.hasGroupSequence() && (groups == null || groups.length == 0)) {
			return this.groupSequenceValidator.validateValue(beanType, propertyName, value, groups);
		}		
		return this.coreValidator.validateValue(beanType, propertyName, value, groups);

	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return this.coreValidator.getConstraintsForClass(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorImpl.class.equals(type)) {
			return (T) new ValidatorImpl();
		} else if (GroupSequenceValidatorImpl.class.equals(type)) {
			return (T) new GroupSequenceValidatorImpl();
		} else if (CoreValidatorImpl.class.equals(type)) {
			return (T) new CoreValidatorImpl();
		}
		throw new ValidationException("This API does not support unwrapping " + type.getName() + ".");
	}
}
