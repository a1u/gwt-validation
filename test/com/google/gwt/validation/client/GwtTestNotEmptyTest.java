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
import com.google.gwt.validation.client.NotEmptyValidator;

public class GwtTestNotEmptyTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testNotEmpty() {
		
		final NotEmptyValidator nev = new NotEmptyValidator();
		assertTrue("Should be true for not empty.", nev.isValid("not_an_empty_string"));
		
	}
	
	@Test
	public void testIsEmpty() {
		final NotEmptyValidator nev = new NotEmptyValidator();
		assertFalse("Should be false for empty.", nev.isValid(""));
	}

	@Test
	public void testIsAllSpaces() {
		final NotEmptyValidator nev = new NotEmptyValidator();
		assertFalse("Should be false for all spaces.", nev.isValid("                                  "));
	}
	
}
