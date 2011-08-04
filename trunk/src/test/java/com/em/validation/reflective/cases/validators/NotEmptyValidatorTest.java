package com.em.validation.reflective.cases.validators;

import org.junit.Test;

import com.em.validation.client.core.validators.CoreNotEmptyValidatorTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class NotEmptyValidatorTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testNotEmpty() {
		CoreNotEmptyValidatorTest.testNotEmpty(this);
	}
	
}
