package com.google.gwt.validation.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.validation.client.AssertFalseValidator;

public class AssertFalseServerTest {
	
	@Test
	public void testFalseValidation() {
		//create validator
		AssertFalseValidator afv = new AssertFalseValidator();
		
		//test
		assertTrue("Validation of false should return true.",afv.isValid(false));		
	}
	
	@Test
	public void testTrueValidation() {
		//create validator
		AssertFalseValidator afv = new AssertFalseValidator();
		
		//test
		assertFalse("Validation of true should return false.",afv.isValid(true));
	}
	
}
