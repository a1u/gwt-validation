package com.em.validation.client;

/* 
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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class ValidatorImpl implements Validator{
	
	private ConstraintValidatorFactory cvFactory = null;
	
	private ValidatorContext context = null;
	
	private TraversableResolver resolver = null;
	
	private MessageInterpolator interpolator = null;
	
	public ValidatorImpl() {		
		this.cvFactory = ValidatorFactoryImpl.INSTANCE.getConstraintValidatorFactory();
		this.context = ValidatorFactoryImpl.INSTANCE.usingContext();
		this.resolver = ValidatorFactoryImpl.INSTANCE.getTraversableResolver();
		this.interpolator = ValidatorFactoryImpl.INSTANCE.getMessageInterpolator();
	}	

	private <T> Set<ConstraintViolation<T>> validate(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, T object, Class<?>... groups) {
		//get the bean descriptor
		BeanDescriptor bean = this.getConstraintsForClass(object.getClass());
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//validate each constrained property individually
		for(PropertyDescriptor property : bean.getConstrainedProperties()) {
			violations.addAll(this.validateProperty(validationCache, object, property.getPropertyName(), groups));
		}
		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = new HashMap<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>>();
		//validate
		return this.validate(validationCache, object, groups);
	}

	@SuppressWarnings("unchecked")
	private <T> Set<ConstraintViolation<T>> validateProperty(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, T object, String propertyName, Class<?>... groups) {
		//get reflector to get property value
		IReflector<T> reflector = ReflectorFactory.INSTANCE.getReflector((Class<T>)object.getClass());
		Object value = reflector.getValue(propertyName, object);

		//validate property
		return this.validateValue(validationCache,(Class<T>)object.getClass(), propertyName, value, groups);
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = new HashMap<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>>();
		//validate
		return this.validateProperty(validationCache, object, propertyName, groups);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Set<ConstraintViolation<T>> validateValue(Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache, Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();

		//get the map of property names to the property values found on the given beanType
		Map<String,Map<Object,Set<ConstraintViolation<?>>>> propertyMap = validationCache.get(beanType);
		//create empty property->value map
		if(propertyMap == null) {
			propertyMap = new HashMap<String, Map<Object,Set<ConstraintViolation<?>>>>();
		}

		//get map of values that have already been seen to the constraint violations that were already provided
		Map<Object,Set<ConstraintViolation<?>>> valueMap = propertyMap.get(propertyName);
		//create empty value->constraint violation map
		if(valueMap == null) {
			valueMap = new HashMap<Object, Set<ConstraintViolation<?>>>();
		}
		
		//get the constraints already seen for that value
		Set<ConstraintViolation<?>> cachedViolations = valueMap.get(value);
		
		if(cachedViolations == null) {
			//get the bean descriptor and the property descriptor
			BeanDescriptor bean = this.getConstraintsForClass(beanType);
			PropertyDescriptor property = bean.getConstraintsForProperty(propertyName);
			
			//get constraints for beanType.propertyName
			Set<ConstraintDescriptor<?>> constraints  = property.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();
			
			//loop and validate
			for(ConstraintDescriptor<?> descriptor : constraints) {
				violations.addAll(this.validateConstraint(beanType, descriptor, value));
			}

			//update maps back into cache
			Set<ConstraintViolation<?>> insertSet = new LinkedHashSet<ConstraintViolation<?>>();
			insertSet.addAll(violations);
			valueMap.put(value, insertSet);
			propertyMap.put(propertyName, valueMap);
			validationCache.put(beanType, propertyMap);
			
			//if the property is cascaded, follow the rabbit hole so that it can pick up the earlier cascades
			if(property.isCascaded()) {
				//perform validation on each map value
				if(value instanceof Map) {
					for(Object key : ((Map)value).keySet()) {
						Object subValue = ((Map)value).get(key);
						
						Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();

						cascadeViolations.addAll(this.validate(validationCache, subValue, groups));	
						
						for(ConstraintViolation<?> violation : cascadeViolations) {
							ConstraintViolation<T> convertedViolation = new ConstraintViolationImpl<T>();
							
							//todo: get details from violation object and populate converted violation
							
							violations.add(convertedViolation);
						}
					}
				//perform validation on each iterable object
				} else if (value instanceof Iterable) {
					for(Object subValue : ((Iterable)value)) {
						Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();

						cascadeViolations.addAll(this.validate(validationCache, subValue, groups));	
						
						for(ConstraintViolation<?> violation : cascadeViolations) {
							ConstraintViolation<T> convertedViolation = new ConstraintViolationImpl<T>();
							
							//todo: get details from violation object and populate converted violation
							
							violations.add(convertedViolation);
						}
					}
				//perform validation on the object itself
				} else {
					Set<ConstraintViolation<Object>> cascadeViolations = new LinkedHashSet<ConstraintViolation<Object>>();

					cascadeViolations.addAll(this.validate(validationCache, value, groups));	

					for(ConstraintViolation<?> violation : cascadeViolations) {
						ConstraintViolation<T> convertedViolation = new ConstraintViolationImpl<T>();
						
						//todo: get details from violation object and populate converted violation
						
						violations.add(convertedViolation);
					}
				}
				
			}
		} else {
			violations.addAll((Collection<? extends ConstraintViolation<T>>) cachedViolations);
		}
		
		return violations;
	}
	
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		//create empty map
		Map<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>> validationCache = new HashMap<Class<?>,Map<String,Map<Object,Set<ConstraintViolation<?>>>>>();
		//call value with pre-provided (empty) map cache
		return this.validateValue(validationCache, beanType, propertyName, value, groups);
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return DescriptorFactory.INSTANCE.getBeanDescriptor(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> type) {
		if(ValidatorImpl.class.equals(type)) {
			return (T) new ValidatorImpl();
		}
		throw new ValidationException("This API only supports unrapping " + ValidatorImpl.class.getName() + " (and not " + type.getName() + ").");
	}

	@SuppressWarnings("unchecked")
	private <T> Set<ConstraintViolation<T>> validateConstraint(Class<T> beanType, ConstraintDescriptor<?> descriptor, Object value) {
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//only do this part of the validation if there is at least one constraint validator
		if(!descriptor.getConstraintValidatorClasses().isEmpty()) {
			//get the validator
			Class<? extends ConstraintValidator<? extends Annotation, T>> validatorClass = (Class<? extends ConstraintValidator<? extends Annotation, T>>) descriptor.getConstraintValidatorClasses().get(0);
		
			//if a validator class was found, create the validator and begin validation
			if(validatorClass != null) {
				try {
					//default to true
					boolean result = true;
					
					@SuppressWarnings("rawtypes")
					ConstraintValidator cValidator = this.cvFactory.getInstance(validatorClass);
					if(cValidator != null) {
						cValidator.initialize(descriptor.getAnnotation());
						result = cValidator.isValid(value, (javax.validation.ConstraintValidatorContext)null);
					}		
					
					//when the result is false, create a constraint violation for the local element
					if(!result) {
						//craft single constraint violation on this node
						ConstraintViolation<T> localViolation = new ConstraintViolationImpl<T>();
						
						//todo: meat of the violation, message, etc
						
						//add local violation
						violations.add(localViolation);
					}
	
				} catch (ValidationException vex) {
					//todo: log that the validator cValidator was skipped b/c of a validation error
				}			
			}
		}
		
		//composed violation
		Set<ConstraintViolation<T>> composedViolations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//do composing constraints
		if(!descriptor.getComposingConstraints().isEmpty()) {
			//get the results for all of the composed constraints
			for(ConstraintDescriptor<?> composedOf : descriptor.getComposingConstraints()) {
				Set<ConstraintViolation<T>> local = this.validateConstraint(beanType, composedOf, value);
				composedViolations.addAll(local);
			}
		}

		//if this violation isn't being reported as a single violation, report all of the composing violations
		if(!descriptor.isReportAsSingleViolation()) {
			violations.addAll(composedViolations);
		}				
		
		return violations;
	}
	
}
