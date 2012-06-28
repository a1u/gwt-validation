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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_045.Defect45_Example;
import com.em.validation.client.model.defects.defect_045.Defect45_Example.Defect45_Group;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_045 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_045() {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Defect45_Example ex = new Defect45_Example();
		
		Assert.assertEquals(0, validator.validate(ex).size());
		Assert.assertEquals(0, validator.validate(ex,Default.class).size());
		Assert.assertEquals(1, validator.validate(ex,Defect45_Group.class).size());
		
	}
	
}
