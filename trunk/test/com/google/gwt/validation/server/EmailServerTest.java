package com.google.gwt.validation.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.gwt.validation.client.EmailValidator;

public class EmailServerTest {
	
	@Test
	public void testEmail() {
		
		//create email validator
		EmailValidator ev = new EmailValidator();
		
		//good emails
		assertTrue("Good Email.",ev.isValid("person@place.com"));
		assertTrue("Good Email.",ev.isValid("person.thing@place.info"));
		assertTrue("Good Email.",ev.isValid("person2_a_place@intoit.edu"));
		assertTrue("Good Email.",ev.isValid("t12h12in12g@citadel.edu"));
		assertTrue("Good Email.",ev.isValid("force_it+work@gmail.com"));
		assertTrue("Good Email.",ev.isValid("areallylongemail12345____51256.email@email.com"));
		
		//bad emails
		assertFalse("Bad email.",ev.isValid("really.com"));
		assertFalse("Bad email.",ev.isValid("not_really21@@@.come@"));
		assertFalse("Bad email.",ev.isValid("asdf@asdf"));
		assertFalse("Bad email.",ev.isValid("not@com@com"));
		assertFalse("Bad email.",ev.isValid("not_gmail.com"));
		assertFalse("Bad email.",ev.isValid("vlkajlkj@@@aldjkflsdkfj.com"));		
		
	}
	
}
