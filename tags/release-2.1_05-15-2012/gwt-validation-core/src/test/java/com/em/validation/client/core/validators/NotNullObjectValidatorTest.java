package com.em.validation.client.core.validators;

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

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Test;

import com.em.validation.client.ConstraintValidatorContextImpl;
import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.validators.NotNullObjectValidator;

public class NotNullObjectValidatorTest extends GwtValidationBaseTestCase {

	@Test
	public void testConstraintGeneration() {

		// constraint validator context
		ConstraintValidatorContext context = new ConstraintValidatorContextImpl(null);
		
		// factory cannot be null
		assertNotNull(this.getReflectorFactory());

		// get bean descriptor for an object that has not null on it
		BeanDescriptor testClassDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(TestClass.class);

		// get prop descriptor for NotNull property
		PropertyDescriptor publicTestStringDescriptor = testClassDescriptor.getConstraintsForProperty("publicTestString");

		// get notnull constraint
		NotNull notNull = null;
		for (ConstraintDescriptor<?> constraintDescriptor : publicTestStringDescriptor.getConstraintDescriptors()) {
			if (NotNull.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				notNull = (NotNull) constraintDescriptor.getAnnotation();
				break;
			}
		}

		// must actually retrieve notNull constraint
		assertNotNull(notNull);

		// create validator at runtime
		NotNullObjectValidator notNullValidator = new NotNullObjectValidator();
		notNullValidator.initialize(notNull);

		// validate a few objects
		assertFalse(notNullValidator.isValid(null, context));
		assertTrue(notNullValidator.isValid("", context));
		assertTrue(notNullValidator.isValid(12, context));
		assertTrue(notNullValidator.isValid(0.0, context));
		assertTrue(notNullValidator.isValid(true, context));
		assertTrue(notNullValidator.isValid("", context));
	}

}
