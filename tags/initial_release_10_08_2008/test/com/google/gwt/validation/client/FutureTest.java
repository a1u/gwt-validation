package com.google.gwt.validation.client;

import java.util.Date;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.FutureValidator;

public class FutureTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testInFuture() {
		
		//create future validator
		FutureValidator fv = new FutureValidator();
		
		//create future date
		Date d = new Date((new Date()).getTime() + 10000000);
	
		//test
		assertTrue("New date is in future", fv.isValid(d));
	}
	
	@Test
	public void testInPast() {

		//create future validator
		FutureValidator fv = new FutureValidator();
		
		//create past date
		Date d = new Date(0);
	
		//test
		assertFalse("New date is in past", fv.isValid(d));		
	}
	
}
