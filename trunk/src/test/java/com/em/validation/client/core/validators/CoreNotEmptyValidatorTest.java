package com.em.validation.client.core.validators;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.model.validation.notempty.ShouldNotBeEmptyButIs;

public class CoreNotEmptyValidatorTest extends GwtValidationBaseTestCase {

	public static void testNotEmpty(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		ShouldNotBeEmptyButIs empty = new ShouldNotBeEmptyButIs();
		
		Set<ConstraintViolation<ShouldNotBeEmptyButIs>> violations = validator.validate(empty);
		
		testCase.localAssertEquals(4, violations.size());
	}
	
}
