package com.google.gwt.validation.server;

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
