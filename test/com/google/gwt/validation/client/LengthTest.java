package com.google.gwt.validation.client;

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.LengthValidator;

public class LengthTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}
	
	private Length l = new Length(){

		public String[] groups() {
			return null;
		}

		public int maximum() {
			return 10;
		}

		public String message() {
			return null;
		}

		public int minimum() {
			return 5;
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};
	
	@Test
	public void testUnderLength() {
		
		//create length constraint
		LengthValidator lv = new LengthValidator();
		lv.initialize(this.l);
		
		//create test string
		String testString =  "1234";
		
		//assert false on validation
		assertFalse("Validation of test string (length 4) should be under minimum and return false.", lv.isValid(testString));
	}
	
	@Test
	public void testWithinLength() {
		//create length constraint
		LengthValidator lv = new LengthValidator();
		lv.initialize(this.l);
		
		//create test string
		String testString =  "123456";
		
		//assert false on validation
		assertTrue("Validation of test string (length 6) should be within limits return true.", lv.isValid(testString));
	}
	
	@Test
	public void testOverLength() {
		//create length constraint
		LengthValidator lv = new LengthValidator();
		lv.initialize(this.l);
		
		//create test string
		String testString =  "1234567891011";
		
		//assert false on validation
		assertFalse("Validation of test string (length 13) should be over maximum and return false.", lv.isValid(testString));
	}
	
}
