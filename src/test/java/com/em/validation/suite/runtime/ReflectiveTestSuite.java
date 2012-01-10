package com.em.validation.suite.runtime;

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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.client.core.ConstraintValidatorFactoryTest;
import com.em.validation.client.core.ConstraintsTest;
import com.em.validation.client.core.MetadataTest;
import com.em.validation.client.core.ValidatorTest;
import com.em.validation.client.core.messages.MessageTest;
import com.em.validation.client.core.reflector.ReflectorTest;
import com.em.validation.reflective.cases.messages.RebindMessageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DefectTestSuite.class,
	ConstraintsTest.class,
	MetadataTest.class,
	ExampleTestSuite.class,
	ValidatorImplementationTestSuite.class,
	ConstraintValidatorFactoryTest.class,
	ValidatorTest.class,
	MessageTest.class,
	RebindMessageTest.class,
	ReflectorTest.class
})
public class ReflectiveTestSuite {
	
}
