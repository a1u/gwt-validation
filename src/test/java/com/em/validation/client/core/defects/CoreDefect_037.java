package com.em.validation.client.core.defects;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.client.model.defects.defect_037.Action;
import com.em.validation.client.model.defects.defect_037.BaseAction;
import com.em.validation.client.model.defects.defect_037.Login;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreDefect_037 {

	public static void testBehaviorOfIssue_037(ITestCase testCase) {
		
		IReflectorFactory factory = testCase.getReflectorFactory();
		
		IReflector<Login> loginReflector = factory.getReflector(Login.class);
		IReflector<BaseAction> baseActionReflector = factory.getReflector(BaseAction.class);
		@SuppressWarnings("rawtypes")
		IReflector<Action> actionReflector = factory.getReflector(Action.class);
		
		//so, what we're checking for here is either that the reflector doesn't exist (gwt mode) or that the reflector
		//exists and that the constraints that are available are null (runtime mode). either way, we want the login reflector
		//to be available and have constraints and the other two to not have any (or not be available).
		testCase.localAssertTrue(loginReflector != null && loginReflector.getConstraintDescriptors().size() > 0);
		testCase.localAssertTrue(baseActionReflector == null || baseActionReflector.getConstraintDescriptors().size() == 0);
		testCase.localAssertTrue(actionReflector == null || actionReflector.getConstraintDescriptors().size() == 0);
		
		//build login objects
		Login accepted = new Login("you-two","123456");
		Login usernameEmpty = new Login("","1233445");
		Login passwordTooShort = new Login("youtwoagain","123");
		
		//create validator
		ValidatorFactory vFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		Validator validator = vFactory.getValidator();
		
		//collect constraint violations
		Set<ConstraintViolation<Login>> noneExpected = validator.validate(accepted);
		Set<ConstraintViolation<Login>> usernameExpected = validator.validate(usernameEmpty);
		Set<ConstraintViolation<Login>> passwordExpected = validator.validate(passwordTooShort);
		
		//make assertions against validation objects.  we're expecing the first one to have no problems and the 
		//other two to have problems as they were engineered to have.
		testCase.localAssertTrue(noneExpected.isEmpty());
		testCase.localAssertFalse(usernameExpected.isEmpty());
		testCase.localAssertFalse(passwordExpected.isEmpty());
	}
	
}
