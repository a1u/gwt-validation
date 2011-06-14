package com.google.gwt.validation.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.google.gwt.validation.client.PastValidator;

public class PastServerTest {

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
