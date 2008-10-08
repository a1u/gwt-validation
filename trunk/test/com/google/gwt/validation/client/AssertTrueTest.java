package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.AssertTrueValidator;

public class AssertTrueTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testTrueValidation() {
		//create validator
		AssertTrueValidator atv = new AssertTrueValidator();
		
		//test
		assertTrue("Validation of true should return true.",atv.isValid(true));		
	}
	
	@Test
	public void testFalseValidation() {
		//create validator
		AssertTrueValidator atv = new AssertTrueValidator();
		
		//test
		assertFalse("Validation of false should return false.",atv.isValid(false));
	}
	
}
