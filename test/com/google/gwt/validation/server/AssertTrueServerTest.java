package com.google.gwt.validation.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.validation.client.AssertTrueValidator;

public class AssertTrueServerTest {
	
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
