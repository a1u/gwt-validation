package com.em.validation.reflective.cases.messages;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

import com.em.validation.client.messages.MessageResolver;

public class RebindMessageTest {

	@Test
	/**
	 * Test method because gwt client will not / cannot support Locale directly (uses GwtLocale)
	 * 
	 */
	public void testOtherLocales() {
		MessageResolver resolver = MessageResolver.INSTANCE;

		Locale locale = Locale.FRANCE;
		String message = resolver.getLocalizedMessageTemplate("javax.validation.constraints.AssertFalse.message",locale);
		
		Assert.assertEquals("NON MERDE!",message);		
		
		locale = new Locale("ES");

		message = resolver.getLocalizedMessageTemplate("javax.validation.constraints.AssertTrue.message",locale);
		
		Assert.assertEquals("YO SOY EL NINO!",message);		
	}
	
}
