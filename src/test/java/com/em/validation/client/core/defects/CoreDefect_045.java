package com.em.validation.client.core.defects;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import com.em.validation.client.model.defects.defect_045.Defect45_Example;
import com.em.validation.client.model.defects.defect_045.Defect45_Example.Defect45_Group;
import com.em.validation.client.model.tests.ITestCase;

public class CoreDefect_045 {

	public static void testBehaviorOfIssue_045(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Defect45_Example ex = new Defect45_Example();
		
		testCase.localAssertEquals(0, validator.validate(ex).size());
		testCase.localAssertEquals(0, validator.validate(ex,Default.class).size());
		testCase.localAssertEquals(1, validator.validate(ex,Defect45_Group.class).size());
		
	}
	
}
