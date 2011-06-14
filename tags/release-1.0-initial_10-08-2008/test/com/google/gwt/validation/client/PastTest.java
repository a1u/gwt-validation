package com.google.gwt.validation.client;

import java.util.Date;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.PastValidator;

public class PastTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testInFuture() {
		
		//create future validator
		PastValidator pv = new PastValidator();
		
		//create future date
		Date d = new Date((new Date()).getTime() + 10000000);
	
		//test
		assertFalse("New date is in future", pv.isValid(d));
	}
	
	@Test
	public void testInPast() {

		//create future validator
		PastValidator pv = new PastValidator();
		
		//create past date
		Date d = new Date(0);
	
		//test
		assertTrue("New date is in past", pv.isValid(d));		
	}
	
}
