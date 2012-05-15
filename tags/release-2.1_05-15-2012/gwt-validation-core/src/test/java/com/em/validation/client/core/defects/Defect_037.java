package com.em.validation.client.core.defects;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_037.Action;
import com.em.validation.client.model.defects.defect_037.BaseAction;
import com.em.validation.client.model.defects.defect_037.Login;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class Defect_037 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_037() {
		
		IReflectorFactory factory = this.getReflectorFactory();
		
		IReflector loginReflector = factory.getReflector(Login.class);
		IReflector baseActionReflector = factory.getReflector(BaseAction.class);
		IReflector actionReflector = factory.getReflector(Action.class);
		
		//so, what we're checking for here is either that the reflector doesn't exist (gwt mode) or that the reflector
		//exists and that the constraints that are available are null (runtime mode). either way, we want the login reflector
		//to be available and have constraints and the other two to not have any (or not be available).
		Assert.assertTrue(loginReflector != null && loginReflector.getConstraintDescriptors().size() > 0);
		Assert.assertTrue(baseActionReflector == null || baseActionReflector.getConstraintDescriptors().size() == 0);
		Assert.assertTrue(actionReflector == null || actionReflector.getConstraintDescriptors().size() == 0);
		
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
		Assert.assertTrue(noneExpected.isEmpty());
		Assert.assertFalse(usernameExpected.isEmpty());
		Assert.assertFalse(passwordExpected.isEmpty());
	}
	
}
