package com.google.gwt.validation.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.NotNullValidator;

public class NotNullTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

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
