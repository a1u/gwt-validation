package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.AssertFalseValidator;

public class GwtTestAssertFalseTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testFalseValidation() {
		//create validator
		final AssertFalseValidator afv = new AssertFalseValidator();
		
		//test
		assertTrue("Validation of false should return true.",afv.isValid(false));		
	}
	
	@Test
	public void testTrueValidation() {
		//create validator
		final AssertFalseValidator afv = new AssertFalseValidator();
		
		//test
		assertFalse("Validation of true should return false.",afv.isValid(true));
	}
	
}
