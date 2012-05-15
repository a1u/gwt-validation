package com.em.validation.client.gwt.serialization;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.util.Set;

import javax.validation.ConstraintViolation;

import junit.framework.Assert;

import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.services.ValidationService;
import com.em.validation.client.services.ValidationServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtSerializationTest extends GwtValidationBaseTestCase {

	
	public void testSerializationGet() {
		
		TestClass testObject = new TestClass();
		
		ValidationServiceAsync service = GWT.create(ValidationService.class);
		
		service.validate(testObject, new AsyncCallback<Set<ConstraintViolation<TestClass>>>() {
			
			@Override
			public void onSuccess(Set<ConstraintViolation<TestClass>> result) {
				Assert.assertNotNull(result);
				
				Assert.assertTrue(result.size() > 0);
				
				finishTest();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				
				Assert.fail("Could not serialize or validate on the server side: " + caught.getMessage());
				
				finishTest();
			}
		});

		this.delayTestFinish(1000);
	}
	
	@Override
	public String getModuleName() {
		return "com.em.validation.ValidationTest";
	}
	
}
