package com.google.gwt.validation.server;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.PatternValidator;

public class PatternServerTest {
	
	private Pattern emailPattern = new Pattern(){

		public String[] groups() {
			return null;
		}

		public String message() {
			return null;
		}

		public String pattern() {
			return "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};
	
	private Pattern websitePattern = new Pattern(){

		public String[] groups() {
			return null;
		}

		public String message() {
			return null;
		}

		public String pattern() {
			return "(http[s]*://|)(([^/:@]+\\.)+[A-Za-z]{2,4})/*";
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};	
	
	@Test
	public void testEmailPattern() {
		
		//init
		PatternValidator pv = new PatternValidator();
		pv.initialize(this.emailPattern);
		
		//email
		String email = "some.guy@hasdomain.com";
		String notEmail = "some@@@domain...coooom";
		
		//assert true with pattern
		assertTrue("Email matches pattern.", pv.isValid(email));
		
		//assert false with pattern
		assertFalse("Not email does not match pattern.", pv.isValid(notEmail));
	}
	
	@Test
	public void testWebsitePattern() {
		
		//init
		PatternValidator pv = new PatternValidator();
		pv.initialize(this.websitePattern);

		//create string
		String website = "http://www.google.com";
		String notWebsite = "http[]iam/not/site";
		
		//assert true with pattern
		assertTrue("Website matches pattern.", pv.isValid(website));
		
		//assert false with pattern
		assertFalse("Not website does not match pattern.", pv.isValid(notWebsite));
	}
	
}
