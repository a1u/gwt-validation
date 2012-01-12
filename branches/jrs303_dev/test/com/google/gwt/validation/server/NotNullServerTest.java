package com.google.gwt.validation.server;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.validation.client.NotNullValidator;

public class NotNullServerTest {

	@Test
	public void testNotNull() {
		
		NotNullValidator  nnv = new NotNullValidator();
		assertTrue("Should be true for not null.", nnv.isValid(new String("")));
		
	}
	
	@Test
	public void testIsNull() {
		NotNullValidator nnv = new NotNullValidator();
		assertFalse("Should be false for null.", nnv.isValid(null));
	}

}