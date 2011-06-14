package com.google.gwt.validation.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.google.gwt.validation.client.FutureValidator;

public class FutureServerTest {

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
