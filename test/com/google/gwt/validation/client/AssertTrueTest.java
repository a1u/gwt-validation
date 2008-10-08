package com.google.gwt.validation.client;

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
