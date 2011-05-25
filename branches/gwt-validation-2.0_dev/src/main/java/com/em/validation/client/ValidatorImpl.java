package com.em.validation.client;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
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

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		//get the bean descriptor
		BeanDescriptor bean = this.getConstraintsForClass(object.getClass());
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//validate each constrained property individually
		for(PropertyDescriptor property : bean.getConstrainedProperties()) {
			violations.addAll(this.validateProperty(object, property.getPropertyName(), groups));
		}
		
		return violations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		//get reflector to get property value
		IReflector<T> reflector = ReflectorFactory.INSTANCE.getReflector((Class<T>)object.getClass());
		Object value = reflector.getValue(propertyName, object);
		
		//validate property
		return this.validateValue((Class<T>)object.getClass(), propertyName, value, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,	String propertyName, Object value, Class<?>... groups) {
		//get constraints for beanType.propertyName
		Set<ConstraintDescriptor<?>> constraints = new LinkedHashSet<ConstraintDescriptor<?>>();
		
		//create empty constraint violation set
		Set<ConstraintViolation<T>> violations = new LinkedHashSet<ConstraintViolation<T>>();
		
		//get the bean descriptor and the property descriptor
		BeanDescriptor bean = this.getConstraintsForClass(beanType);
		PropertyDescriptor property = bean.getConstraintsForProperty(propertyName);
		
		//find the properties
		constraints = property.findConstraints().unorderedAndMatchingGroups(groups).getConstraintDescriptors();
		
		//loop and validate
		for(ConstraintDescriptor<?> descriptor : constraints) {
			violations.addAll(this.validateConstraint(beanType, descriptor, value));
		}
		
		//if the property is cascaded, follow the rabbit hole
		if(property.isCascaded()) {
			
		}
		
		return violations;
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
						ConstraintViolation<T> localViolation = null;
						
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
