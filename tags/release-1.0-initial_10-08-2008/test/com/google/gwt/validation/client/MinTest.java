package com.google.gwt.validation.client;

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.MinValidator;

public class MinTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}
	
	//annotation instance
	private Min m = new Min() {

		public String[] groups() {
			return null;
		}

		public int minimum() {
			return 2007;
		}

		public String message() {
			return null;
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};
	
	@Test
	public void testUnderMin() {
	
		//create annotation
		MinValidator mv = new MinValidator();
		mv.initialize(this.m);
		
		//values
		int i =  12;
		double d =  12;
		float f = 12;
		long l = 12;
		
		//assertions
		assertFalse("12i is under minimum of 2007",mv.isValid(i));
		assertFalse("12d is under minimum of 2007",mv.isValid(d));
		assertFalse("12f is under minimum of 2007",mv.isValid(f));
		assertFalse("12l is under minimum of 2007",mv.isValid(l));
	}
	
	@Test
	public void testOverMin() {
		//create annotation
		MinValidator mv = new MinValidator();
		mv.initialize(this.m);
		
		//values
		int i =  12000;
		double d =  12000;
		float f = 12000;
		long l = 12000;
		
		//assertions
		assertTrue("12000i is over minimum of 2007",mv.isValid(i));
		assertTrue("12000d is over minimum of 2007",mv.isValid(d));
		assertTrue("12000f is over minimum of 2007",mv.isValid(f));
		assertTrue("12000l is over minimum of 2007",mv.isValid(l));
	}
}
