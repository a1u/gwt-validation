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

import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.defects.defect_005.StrangeCapitalization;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;

public class Defect_005 extends GwtValidationBaseTestCase {

	@Test
	public void testStrangeCapitalization() {
		StrangeCapitalization capitalization = new StrangeCapitalization();	
		
		IReflector reflector = this.getReflectorFactory().getReflector(capitalization.getClass()); 
		
		//no null reflectors
		assertNotNull(reflector);
		
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
		
		//check reflector
		assertEquals("uri", reflector.getValue("URIs", capitalization));
		assertEquals("nonstandard", reflector.getValue("nOnStAnDarD", capitalization));
		assertEquals("alllowercase", reflector.getValue("alllowercase", capitalization));
		assertEquals("alluppercase", reflector.getValue("ALLUPPERCASE", capitalization));		
		
		//check descriptor
		assertEquals(2,descriptor.getConstraintsForProperty("nOnStAnDarD").getConstraintDescriptors().size());		
	}
	
}
