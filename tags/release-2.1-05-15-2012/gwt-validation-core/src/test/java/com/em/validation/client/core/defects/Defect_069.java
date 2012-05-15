package com.em.validation.client.core.defects;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_069.ValidConstraintChildClass;
import com.em.validation.client.model.defects.defect_069.ValidConstraintTestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_069 extends GwtValidationBaseTestCase {
	
	@Test
	public void testBehaviorOfIssue_69() {

		//get the validator factory and the validator
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
				
		//create the test object
		ValidConstraintTestClass testClass = new ValidConstraintTestClass();
		
		//violations, should be 2, two child properties should get busted on "not null"
		Set<ConstraintViolation<ValidConstraintTestClass>> violations = validator.validate(testClass);
		Assert.assertEquals("Should start with two NotNull violations", 2, violations.size());
		
		//set as not null, should go to four.
		ValidConstraintChildClass childOne = new ValidConstraintChildClass();
		ValidConstraintChildClass childTwo = new ValidConstraintChildClass();
		
		testClass.setChildOne(childOne);
		testClass.setChildTwo(childTwo);
		
		//re-validate
		violations = validator.validate(testClass);
		Assert.assertEquals("Should be four NotNull violations, from the child nodes each having two", 4, violations.size());
	}

}
