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

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_041.Defect41DomainObject;
import com.em.validation.client.model.defects.defect_041.Defect41Enum;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_041 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_041() {
		
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
			Assert.assertEquals(1, violations.size());
		} catch (Exception ex) {
			Assert.fail("Could not validate object of type: " + object.getClass().getName() + "(" + ex.getClass().getName() + " : " + ex.getMessage() + ")");
		}
		
	}
	
}
