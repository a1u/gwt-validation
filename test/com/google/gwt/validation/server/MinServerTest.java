package com.google.gwt.validation.server;

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
import static org.junit.Assert.*;

import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.MinValidator;

public class MinServerTest {
	
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
