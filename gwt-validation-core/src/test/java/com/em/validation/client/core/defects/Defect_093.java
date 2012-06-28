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

import com.em.validation.client.model.defects.defect_093.FailingModelObject;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_093 extends GwtValidationBaseTestCase {

    @Test
    public void testBehaviorOfScanningValidatorClasses() {

            //get the validator factory and the validator
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

            Set<ConstraintViolation<FailingModelObject>> constraintViolations = validator.validateValue(FailingModelObject.class, "name", "any non-email value should get two failures");

            Assert.assertEquals(2, constraintViolations.size());
            
            constraintViolations = validator.validateValue(FailingModelObject.class, "name", "email@thing.com");
            
            Assert.assertEquals(1, constraintViolations.size());
    }
	
}
