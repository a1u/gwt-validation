package com.em.validation.client.core;

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
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ValidationException;

import org.junit.Test;

import com.em.validation.client.constraints.NotEmpty;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.validators.NotNullObjectValidator;

public class ConstraintValidatorFactoryTest extends GwtValidationBaseTestCase {

	@Test
	public void testConstraintValidatorFactory() {

		// get the factory from the test constraints
		ConstraintValidatorFactory factory = this.getConstraintValidationFactory();

		// the factory cannot be null
		assertNotNull(factory);

		// the factory returns an instance (for member and declared classes)
		assertNotNull(factory.getInstance(NotNullObjectValidator.class));

		try {
			// get an instance of a factory than COULD NOT be created at runtime
			// or generation time (anonymous)
			factory.getInstance((new ConstraintValidator<NotEmpty, Object>() {
				@Override
				public void initialize(NotEmpty constraintAnnotation) {

				}

				@Override
				public boolean isValid(Object value, ConstraintValidatorContext context) {
					return false;
				}
			}).getClass());
			// execution should not reach this point, an exception should be
			// thrown
			assertTrue("Execution should not reach this point.", false);
		} catch (Exception ex) {
			// a validation exception should be thrown
			assertEquals(ValidationException.class, ex.getClass());
		}
	}

}
