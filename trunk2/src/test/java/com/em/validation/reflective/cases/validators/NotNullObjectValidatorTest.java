package com.em.validation.reflective.cases.validators;

import org.junit.Test;

import com.em.validation.client.core.validators.CoreNotNullObjectValidatorTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class NotNullObjectValidatorTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		CoreNotNullObjectValidatorTest.testConstraintGeneration(this.getReflectorFactory());
	}
}
