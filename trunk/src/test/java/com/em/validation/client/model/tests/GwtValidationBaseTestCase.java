package com.em.validation.client.model.tests;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import javax.validation.ConstraintValidatorFactory;

import com.em.validation.client.ConstraintValidatorFactoryImpl;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;
import com.google.gwt.junit.client.GWTTestCase;

public class GwtValidationBaseTestCase extends GWTTestCase implements ITestCase{
	
	@Override
	public String getModuleName() {
		return "com.em.validation.ValidationTest";
	}

	@Override
	public IReflectorFactory getReflectorFactory() {
		return ReflectorFactory.INSTANCE;
	}

	@Override
	public ConstraintValidatorFactory getConstraintValidationFactory() {
		return ConstraintValidatorFactoryImpl.INSTANCE;
	}

	@Override
	public void localAssertEquals(Object expected, Object actual) {
		GWTTestCase.assertEquals(expected, actual);
	}

	@Override
	public void localAssertEquals(String message, Object expected, Object actual) {
		GWTTestCase.assertEquals(message, expected, actual);		
	}

	@Override
	public void localAssertTrue(boolean actual) {
		GWTTestCase.assertTrue(actual);
	}

	@Override
	public void localAssertTrue(String message, boolean actual) {
		GWTTestCase.assertTrue(message,actual);	
	}

	@Override
	public void localAssertFalse(boolean actual) {
		GWTTestCase.assertFalse(actual); 
	}

	@Override
	public void localAssertFalse(String message, boolean actual) {
		GWTTestCase.assertFalse(message, actual);		
	}
	
	@Override
	public void localAssertNotNull(Object value) {
		GWTTestCase.assertNotNull(value); 
	}

	@Override
	public void localAssertNotNull(String message, Object value) {
		GWTTestCase.assertNotNull(message, value);		
	}
	
	@Override
	public void localFail(String message) {
		GWTTestCase.fail(message);
	}
}
