package com.em.validation.client.cases.validators;

import org.junit.Test;

import com.em.validation.client.core.validators.CoreNotNullObjectValidatorTest;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class NotNullObjectValidatorTest extends GwtValidationBaseTestCase {
	
	@Test
	public void testConstraintGeneration() {
		CoreNotNullObjectValidatorTest.testConstraintGeneration(this.getReflectorFactory());
	}
}
