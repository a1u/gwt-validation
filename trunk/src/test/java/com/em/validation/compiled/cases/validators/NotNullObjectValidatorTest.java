package com.em.validation.compiled.cases.validators;

import org.junit.Test;

import com.em.validation.client.core.validators.CoreNotNullObjectValidatorTest;
import com.em.validation.compiled.model.test.CompiledValidationBaseTestClass;

public class NotNullObjectValidatorTest extends CompiledValidationBaseTestClass {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		CoreNotNullObjectValidatorTest.testConstraintGeneration(this.getReflectorFactory());
	}
}
