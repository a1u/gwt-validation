package com.em.validation.rebind;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintValidator;

import org.junit.Test;

import com.em.validation.client.model.generic.ExtendedInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.rebind.scan.ClassScanner;

public class ClassScannerTest {

	@Test
	public void testConstrainedClassScanner() {
		//get the set of constrained classes on the classpath
		Set<Class<?>> classes = ClassScanner.INSTANCE.getConstrainedClasses();
		
		//verify
		assertTrue(classes.contains(ExtendedInterface.class));
		assertTrue(classes.contains(TestClass.class));
		assertTrue(classes.contains(GroupTestClass.class));
	}
	
	@Test
	public void testPatternMatchingConsrainedClassScanner() {
		//get the constrained classes not matching the given pattern
		Set<Class<?>> classes = ClassScanner.INSTANCE.getConstrainedClasses(".*groups.*");
	
		//verify
		assertTrue(classes.contains(ExtendedInterface.class));
		assertTrue(classes.contains(TestClass.class));
		assertFalse(classes.contains(GroupTestClass.class));
	}
	
	@Test
	public void testPatternMatchingValidatorClassScanner() {
		//get the constrained classes not matching the given pattern
		Set<Class<? extends ConstraintValidator<?, ?>>> classes = ClassScanner.INSTANCE.getConstraintValidatorClasses(".*");
	
		//verify
		assertEquals(0, classes.size());
	}
	
}
