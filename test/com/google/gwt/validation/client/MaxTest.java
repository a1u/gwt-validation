package com.google.gwt.validation.client;

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.MaxValidator;

public class MaxTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	//annotation instance
	private Max m = new Max() {

		public String[] groups() {
			return null;
		}

		public int maximum() {
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
	public void testUnderMax() {
	
		//create annotation
		MaxValidator mv = new MaxValidator();
		mv.initialize(this.m);
		
		//values
		int i =  12;
		double d =  12;
		float f = 12;
		long l = 12;
		
		//assertions
		assertTrue("12i is under maximum of 2007",mv.isValid(i));
		assertTrue("12d is under maximum of 2007",mv.isValid(d));
		assertTrue("12f is under maximum of 2007",mv.isValid(f));
		assertTrue("12l is under maximum of 2007",mv.isValid(l));
	}
	
	@Test
	public void testOverMax() {
		//create annotation
		MaxValidator mv = new MaxValidator();
		mv.initialize(this.m);
		
		//values
		int i =  12000;
		double d =  12000;
		float f = 12000;
		long l = 12000;
		
		//assertions
		assertFalse("12000i is over maximum of 2007",mv.isValid(i));
		assertFalse("12000d is over maximum of 2007",mv.isValid(d));
		assertFalse("12000f is over maximum of 2007",mv.isValid(f));
		assertFalse("12000l is over maximum of 2007",mv.isValid(l));
	}
	
}
