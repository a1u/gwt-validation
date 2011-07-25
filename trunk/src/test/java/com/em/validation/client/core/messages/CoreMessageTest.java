package com.em.validation.client.core.messages;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import java.util.HashMap;
import java.util.Map;

import com.em.validation.client.messages.IMessageResolver;
import com.em.validation.client.messages.MessageResolver;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.model.tests.ITestCase;

public class CoreMessageTest extends GwtValidationBaseTestCase{
	
	public static void testDefaultLocale(ITestCase testCase) {
		IMessageResolver resolver = MessageResolver.INSTANCE;
		
		String message = resolver.getLocalizedMessageTemplate("{javax.validation.constraints.AssertFalse.message}");
		
		assertEquals("I AM FALSE",message);
		
		message = resolver.getLocalizedMessageTemplate("NOT A MESSAGE");
		
		assertEquals("NOT A MESSAGE",message);
	}
	
	public static void testMessageTemplate(ITestCase testCase) {
		IMessageResolver resolver = MessageResolver.INSTANCE;
		
		String input = "{prop} is {one} or {two} ({property}:{prop})";
		
		Map<String,Object> props = new HashMap<String, Object>();
		
		props.put("prop", "size");
		props.put("one", "1");
		props.put("two", "2");
		
		String output = resolver.getMessage(input, props);
		
		assertEquals("size is 1 or 2 ({property}:size)",output);
	}
}
