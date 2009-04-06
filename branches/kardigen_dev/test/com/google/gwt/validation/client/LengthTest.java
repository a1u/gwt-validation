package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

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

	@Test
	public void testAtMinimumLength() {
		//create length constraint
		LengthValidator lv = new LengthValidator();
		lv.initialize(this.l);

		//create test string
		String testString =  "12345";

		//assert false on validation
		assertTrue("Validation of test string (length 5) should be at minimum and return true.", lv.isValid(testString));
	}

	@Test
	public void testAtMaximumLength() {
		//create length constraint
		LengthValidator lv = new LengthValidator();
		lv.initialize(this.l);

		//create test string
		String testString =  "1234567890";

		//assert false on validation
		assertTrue("Validation of test string (length 10) should be at maximum and return true.", lv.isValid(testString));
	}


}
