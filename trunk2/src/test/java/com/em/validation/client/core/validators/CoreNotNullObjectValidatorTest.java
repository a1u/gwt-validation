package com.em.validation.client.core.validators;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.validators.NotNullObjectValidator;

public class CoreNotNullObjectValidatorTest extends GwtValidationBaseTestCase {

	public static void testConstraintGeneration(IReflectorFactory factory) {
		
		//constraint validator context
		ConstraintValidatorContext context = new ConstraintValidatorContext() {

			@Override
			public ConstraintViolationBuilder buildConstraintViolationWithTemplate(String arg0) {
				return null;
			}

			@Override
			public void disableDefaultConstraintViolation() {
				
			}

			@Override
			public String getDefaultConstraintMessageTemplate() {
				return null;
			}
			
		};
		
		//factory cannot be null
		assertNotNull(factory);
		
		//get bean descriptor for an object that has not null on it
		BeanDescriptor testClassDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(TestClass.class);
		
		//get prop descriptor for NotNull property
		PropertyDescriptor publicTestStringDescriptor = testClassDescriptor.getConstraintsForProperty("publicTestString");
		
		//get notnull constraint
		NotNull notNull = null;
		for(ConstraintDescriptor<?> constraintDescriptor : publicTestStringDescriptor.getConstraintDescriptors()) {
			if(NotNull.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				notNull = (NotNull)constraintDescriptor.getAnnotation();
				break;
			}
		}
		
		//must actually retrieve notNull constraint
		assertNotNull(notNull);
		
		//create validator at runtime
		NotNullObjectValidator notNullValidator = new NotNullObjectValidator();
		notNullValidator.initialize(notNull);
		
		//validate a few objects
		assertFalse(notNullValidator.isValid(null, context));
		assertTrue(notNullValidator.isValid(new String(), context));
		assertTrue(notNullValidator.isValid(12, context));
		assertTrue(notNullValidator.isValid(0.0, context));
		assertTrue(notNullValidator.isValid(true, context));
		assertTrue(notNullValidator.isValid("", context));
	}
	
}
