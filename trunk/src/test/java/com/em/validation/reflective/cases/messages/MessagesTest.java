package com.em.validation.reflective.cases.messages;

import java.util.Locale;

import org.junit.Test;

import com.em.validation.client.core.messages.CoreMessageTest;
import com.em.validation.client.messages.MessageResolver;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class MessagesTest extends ReflectiveValidationBaseTestClass {

	@Test
	public void testDefaultLocale() {
		CoreMessageTest.testDefaultLocale(this);
	}
	
	@Test
	public void testMessageTemplate() {
		CoreMessageTest.testMessageTemplate(this);
	}

	@Test
	/**
	 * Test method because gwt client will not / cannot support Locale directly (uses GwtLocale)
	 * 
	 */
	public void testOtherLocales() {
		MessageResolver resolver = MessageResolver.INSTANCE;

		Locale locale = Locale.FRANCE;
		String message = resolver.getLocalizedMessageTemplate("javax.validation.constraints.AssertFalse.message",locale);
		
		this.localAssertEquals("NON MERDE!",message);		
		
		locale = new Locale("ES");

		message = resolver.getLocalizedMessageTemplate("javax.validation.constraints.AssertTrue.message",locale);
		
		this.localAssertEquals("YO SOY EL NINO!",message);		
	}
	
}
