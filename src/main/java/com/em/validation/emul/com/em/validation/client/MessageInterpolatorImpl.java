package com.em.validation.client;

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

import javax.validation.MessageInterpolator;

import com.em.validation.client.messages.MessageResolver;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.server.GwtLocaleFactoryImpl;
import com.google.gwt.i18n.shared.GwtLocale;

public class MessageInterpolatorImpl implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
	
		return this.interpolate(messageTemplate, context, (GwtLocale)null) ;
	}

	public String interpolate(String messageTemplate, Context context, GwtLocale locale) {
		
		MessageResolver resolver = MessageResolver.INSTANCE;
		
		String template = messageTemplate;
		String output = template;
		
		//only need to get the localized version if and when the string starts and ends with { and } with no { or } in between.
		//"{java...thing.Class.message}" is localized
		//"{attribute} of {property} with {property}" is not localized
		if(template.startsWith("{") && template.endsWith("}") && !template.substring(1,template.length() - 1).contains("{") && !template.substring(1,template.length() - 1).contains("}")) {
			 output = resolver.getLocalizedMessageTemplate(template);
		}

		output = resolver.getMessage(output, context.getConstraintDescriptor().getAttributes());			
		
		return output;
	}

	/*
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		
		return messageTemplate;
	}
	*/
}
