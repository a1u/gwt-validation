package com.google.gwt.validation.client;

import java.lang.annotation.Annotation;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.PatternValidator;

public class PatternTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

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
