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
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_040.Person_040;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;

public class Defect_040 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_040() {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		try {
			Person_040 testPerson = new Person_040();
		
			Set<ConstraintViolation<Person_040>> violations = validator.validate(testPerson);
			Assert.assertEquals(1, violations.size());
			
			//get the class descriptor
			BeanDescriptor descriptor = validator.getConstraintsForClass(Person_040.class);
			
			//prove that the property descriptor is simply null
			PropertyDescriptor prop = descriptor.getConstraintsForProperty("upperCaseName");
			Assert.assertTrue(prop == null);
			
			//get a reflector
			IReflector personReflector = this.getReflectorFactory().getReflector(Person_040.class);
			
			//prove property name exists
			Assert.assertTrue(personReflector.getPropertyNames().contains("upperCaseName"));
			Assert.assertFalse(personReflector.getPropertyNames().contains("getUpperCaseName"));
			Assert.assertFalse(personReflector.getPropertyNames().contains("UpperCaseName"));
			
			//prove property is available by reflection
			testPerson.setName("pops");
			Assert.assertEquals("pops should be POPS with uppercase", "POPS", testPerson.getUpperCaseName());
			Assert.assertEquals("pops should be POPS with uppercase", "POPS", personReflector.getValue("upperCaseName", testPerson));
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Should not throw exception during validation.");
		}
	}
	
}