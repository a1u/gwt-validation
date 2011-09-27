package com.em.validation.reflective.cases.validators;

import org.junit.Test;

import com.em.validation.client.core.validators.CoreDateValidatorTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class DateValidatorTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testFutureValidator() {
		CoreDateValidatorTest.testFutureValidator(this);
	}
	
	@Test
	public void testPastValidator() {
		CoreDateValidatorTest.testPastValidator(this);
	}
	
}
