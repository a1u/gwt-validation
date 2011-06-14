package com.google.gwt.validation.server;

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
