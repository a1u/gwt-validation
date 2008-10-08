package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Range;
import com.google.gwt.validation.client.RangeValidator;

public class RangeTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}
	
	private Range r = new Range() {

		public String[] groups() {
			return null;
		}

		public int maximum() {
			return 812;
		}

		public String message() {
			return null;
		}

		public int minimum() {
			return 412;
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};

	@Test
	public void testInRange() {
		
		RangeValidator rv = new RangeValidator();
		rv.initialize(this.r);
		
		int i = 600;
		float f = 600;
		double d = 600;
		long l = 600;
		
		//assertions
		assertTrue("600i is between 412 and 812",rv.isValid(i));
		assertTrue("600d is between 412 and 812",rv.isValid(d));
		assertTrue("600f is between 412 and 812",rv.isValid(f));
		assertTrue("600l is between 412 and 812",rv.isValid(l));
	}
	
	@Test
	public void testOverRange() {

		RangeValidator rv = new RangeValidator();
		rv.initialize(this.r);
	
		int i = 900;
		float f = 900;
		double d = 900;
		long l = 900;
		
		//assertions
		assertFalse("900i is not between 412 and 812",rv.isValid(i));
		assertFalse("900d is not between 412 and 812",rv.isValid(d));
		assertFalse("900f is not between 412 and 812",rv.isValid(f));
		assertFalse("900l is not between 412 and 812",rv.isValid(l));		
	}
	
	@Test
	public void testUnderRange() {
		
		RangeValidator rv = new RangeValidator();
		rv.initialize(this.r);
	
		int i = 200;
		float f = 200;
		double d = 200;
		long l = 200;
		
		//assertions
		assertFalse("200i is not between 412 and 812",rv.isValid(i));
		assertFalse("200d is not between 412 and 812",rv.isValid(d));
		assertFalse("200f is not between 412 and 812",rv.isValid(f));
		assertFalse("200l is not between 412 and 812",rv.isValid(l));	
	
	}
	
}
