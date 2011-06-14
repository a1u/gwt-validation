package com.google.gwt.validation.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.NotEmptyValidator;

public class NotEmptyTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

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
