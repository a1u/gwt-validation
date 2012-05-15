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
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_042 extends GwtValidationBaseTestCase {

	public static class Example_42_A {
		
		@Valid
		private Example_42_B b = new Example_42_B();
		
		@Valid
		@NotNull
		private Example_42_C c = new Example_42_C();

		public Example_42_B getB() {
			return b;
		}

		public void setB(Example_42_B b) {
			this.b = b;
		}

		public Example_42_C getC() {
			return c;
		}

		public void setC(Example_42_C c) {
			this.c = c;
		}		
	}
	
	public static class Example_42_B {
		
		@Size(max=5)
		private String name = "abcdefgh";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Example_42_C {

		@Size(max=5)
		private String name = "abcdefgh";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	@Test
	public void testBehaviorOfIssue_042() {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Example_42_A a = new Example_42_A();
		
		Set<ConstraintViolation<Example_42_A>> violations = validator.validate(a);
		
		Assert.assertEquals(2, violations.size());		
	}
	
}