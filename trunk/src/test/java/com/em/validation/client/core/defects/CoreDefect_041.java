package com.em.validation.client.core.defects;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.client.model.defects.defect_041.Defect41DomainObject;
import com.em.validation.client.model.defects.defect_041.Defect41Enum;
import com.em.validation.client.model.tests.ITestCase;

public class CoreDefect_041 {

	public static void testBehaviorOfIssue_041(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Defect41DomainObject object = new Defect41DomainObject();
		
		try {
			//validate vanilla
			validator.validate(object);
			
			//create set of enum values and set to object
			Set<Defect41Enum> enumValues = new HashSet<Defect41Enum>();
			enumValues.add(Defect41Enum.SECOND_VALUE);
			object.setEnumValues(enumValues);
			
			//validate again, with values (non null) and notice that the passed in set DOES NOT contain the value on the annotation
			Set<ConstraintViolation<Defect41DomainObject>> violations = validator.validate(object);
			testCase.localAssertEquals(1, violations.size());
		} catch (Exception ex) {
			testCase.localFail("Could not validate object of type: " + object.getClass().getName() + "(" + ex.getClass().getName() + " : " + ex.getMessage() + ")");
		}
		
	}
	
}
