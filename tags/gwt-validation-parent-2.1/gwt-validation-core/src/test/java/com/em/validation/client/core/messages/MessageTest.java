package com.em.validation.client.core.messages;

/* 
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.messages.IMessageResolver;
import com.em.validation.client.messages.MessageResolver;
import com.em.validation.client.model.constraint.CreditCard;
import com.em.validation.client.model.message.MessageExampleTestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class MessageTest extends GwtValidationBaseTestCase {

	@Test
	public void testDefaultLocale() {
		IMessageResolver resolver = MessageResolver.INSTANCE;

		String message = resolver.getLocalizedMessageTemplate("{javax.validation.constraints.AssertFalse.message}");
		
		//fix for issue #63 based on patch provided by rodrigue.bouleau (http://code.google.com/p/gwt-validation/issues/detail?id=63)
		if (!message.equalsIgnoreCase("must be false") 
			&& !message.equalsIgnoreCase("NON MERDE!") 
			&& !message.equalsIgnoreCase("NON EL NINO!"))
		{
			Assert.fail("The message has not been translated");
		}
		
		message = resolver.getLocalizedMessageTemplate("NOT A MESSAGE");

		Assert.assertEquals("NOT A MESSAGE", message);
	}

	@Test
	public void testMessageTemplate() {
		IMessageResolver resolver = MessageResolver.INSTANCE;

		String input = "{prop} is {one} or {two} ({property}:{prop})";

		Map<String, Object> props = new HashMap<String, Object>();

		props.put("prop", "size");
		props.put("one", "1");
		props.put("two", "2");

		String output = resolver.getMessage(input, props);

		Assert.assertEquals("size is 1 or 2 ({property}:size)", output);
	}
	
	/**
	 * Tests the message interpolation examples from 4.3.3 of the JSR-303 documentation
	 * 
	 */
	@Test
	public void testJSR303Examples() {

		//get validator
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		//create object
		MessageExampleTestClass test = new MessageExampleTestClass();
		
		//violation set
		Set<ConstraintViolation<MessageExampleTestClass>> violations = validator.validate(test);
		
		//right number
		Assert.assertEquals("Five example violations found.", 5, violations.size());
		
		//check each violation
		for(ConstraintViolation<MessageExampleTestClass> v : violations) {
			if(NotNull.class.equals(v.getConstraintDescriptor().getAnnotation().annotationType())) {
				Assert.assertEquals("Messages must be equal.", "must not be null", v.getMessage());
			} else if(Max.class.equals(v.getConstraintDescriptor().getAnnotation().annotationType())) {
				Assert.assertEquals("Messages must be equal.", "must be less than or equal to 30", v.getMessage());
			} else if(Size.class.equals(v.getConstraintDescriptor().getAnnotation().annotationType())) {
				Assert.assertEquals("Messages must be equal.", "Key must have {5} \\ {15} characters", v.getMessage());
			} else if(Digits.class.equals(v.getConstraintDescriptor().getAnnotation().annotationType())) {
				Assert.assertEquals("Messages must be equal.", "numeric value out of bounds (<9 digits>.<2 digits> expected)", v.getMessage());
			} else if(CreditCard.class.equals(v.getConstraintDescriptor().getAnnotation().annotationType())) {
				Assert.assertEquals("Messages must be equal.", "credit card number not valid", v.getMessage());
			} else {
				fail("Unexpected constraint type found: " + v.getConstraintDescriptor().getAnnotation().annotationType().getName());
			}
		}
	
	}
	
}
