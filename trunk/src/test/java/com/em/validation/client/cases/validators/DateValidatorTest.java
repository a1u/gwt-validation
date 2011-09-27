package com.em.validation.client.cases.validators;

import com.em.validation.client.core.validators.CoreDateValidatorTest;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class DateValidatorTest extends GwtValidationBaseTestCase {
	
	public void testFutureValidator() {
		CoreDateValidatorTest.testFutureValidator(this);
	}
	
	public void testPastValidator() {
		CoreDateValidatorTest.testPastValidator(this);
	}
	
}
