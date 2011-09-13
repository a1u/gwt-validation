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

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.GroupDefinitionException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class GroupSequenceValidatorImpl implements Validator{
	
	private Validator coreValidator = new CoreValidatorImpl();
	
	public GroupSequenceValidatorImpl() {		
		
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		return this.validate(new HashSet<Class<?>>(), object, groups);
	}
	
	private <T> Set<ConstraintViolation<T>> validate(Set<Class<?>> previousGroups, T object, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + object.getClass().getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validate(object, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		return this.validateProperty(new HashSet<Class<?>>(), object, propertyName, groups);
	}

	private <T> Set<ConstraintViolation<T>> validateProperty(Set<Class<?>> previousGroups, T object, String propertyName, Class<?>... groups) {
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(object.getClass());
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + object.getClass().getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validateProperty(object, propertyName, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		return this.validateValue(new HashSet<Class<?>>(), beanType, propertyName, value, groups);
	}
	
	private <T> Set<ConstraintViolation<T>> validateValue(Set<Class<?>> previousGroups, Class<T> beanType,	String propertyName, Object value, Class<?>... groups) { 
		IReflector reflector = ReflectorFactory.INSTANCE.getReflector(beanType);
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>>();
		for(Class<?> group : reflector.getGroupSequence()) {
			if(previousGroups.contains(group)) {
				throw new GroupDefinitionException("The group sequence on " + beanType.getName() + " already contains " + group.getName() + ".  Definitions may not be cyclic or circular");
			}
			
			violations.addAll(this.coreValidator.validateValue(beanType, propertyName, group));
			previousGroups.add(group);
			
			if(!violations.isEmpty()) {
				break;
			}
		}		
		return violations;
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return this.coreValidator.getConstraintsForClass(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(GroupSequenceValidatorImpl.class.equals(type)) {
			return (T) new GroupSequenceValidatorImpl();
		}
		throw new ValidationException("This API only supports unwrapping " + GroupSequenceValidatorImpl.class.getName() + " (and not " + type.getName() + ").");
	}

}
