package com.google.gwt.validation.server;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.validation.client.NotEmptyValidator;

public class NotEmptyServerTest {
	

	@Test
	public void testNotEmpty() {
		
		NotEmptyValidator nev = new NotEmptyValidator();
		assertTrue("Should be true for not empty.", nev.isValid("not_an_empty_string"));
		
	}
	
	@Test
	public void testIsEmpty() {
		NotEmptyValidator nev = new NotEmptyValidator();
		assertFalse("Should be false for empty.", nev.isValid(""));
	}

	@Test
	public void testIsAllSpaces() {
		NotEmptyValidator nev = new NotEmptyValidator();
		assertFalse("Should be false for all spaces.", nev.isValid("                                  "));
	}
	
}
