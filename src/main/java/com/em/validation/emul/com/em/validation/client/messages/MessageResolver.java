package com.em.validation.client.messages;

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

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.em.validation.client.messages.IMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.LocaleInfo;

public enum MessageResolver implements IMessageResolver {

	INSTANCE;
	
	private IMessage gwtLocalizedMessage = null;
	
	private MessageResolver() {
		this.gwtLocalizedMessage = GWT.create(IMessage.class);
	}
	
	@Override
	public String getLocalizedMessageTemplate(String message) {
		String template = message;

		String output = message;
		
		if(template.startsWith("{")) {
			template = template.substring(1);
		}
		
		if(template.endsWith("}")) {
			template = template.substring(0,template.length() - 1);
		}	
		
		try {
			output = this.gwtLocalizedMessage.getString(LocaleInfo.getCurrentLocale().getLocaleName(), template);
		} catch(MissingResourceException mex) {
			mex.printStackTrace();
			output = message;
		}
		if(output == null || output.isEmpty()) {
			output = message;
		}
		return output;
	}

	@Override
	public String getMessage(String messageTemplate, Map<String, Object> properties) {
		return MessageUtil.getMessage(messageTemplate, properties);
	}
	
}
