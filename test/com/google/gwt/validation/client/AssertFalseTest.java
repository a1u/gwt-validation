package com.google.gwt.validation.client;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.AssertFalseValidator;

public class AssertFalseTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

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
