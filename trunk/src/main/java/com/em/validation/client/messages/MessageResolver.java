package com.em.validation.client.messages;

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

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public enum MessageResolver implements IMessageResolver {

	INSTANCE;
	
	private MessageResolver() {
		
	}
	
	@Override
	public String getLocalizedMessageTemplate(String message) {

		Locale locale = Locale.getDefault();
		
		return this.getLocalizedMessageTemplate(message, locale);
	}
	
	public String getLocalizedMessageTemplate(String message, Locale locale) {
		//do not allow default locale
		if(locale == null) {
			locale = Locale.getDefault();
		}
		
		ResourceBundle bundle = null;
		try { 
			bundle = ResourceBundle.getBundle("ValidationMessages", locale);
		} catch (Exception e) {
			try {
				bundle = ResourceBundle.getBundle("ValidationMessages");
			} catch (Exception ex) {
				return message;
			}
		}
		
		if(bundle == null) {
			return message;
		}
		
		String value = message;

		String template = message;
		
		if(template.startsWith("{")) {
			template = template.substring(1);
		}
		
		if(template.endsWith("}")) {
			template = template.substring(0,template.length() - 1);
		}		
		
		try{ 
			value = (String)bundle.getObject(template);
		} catch (Exception ex) {
			//do nothing, return value which is still message
		}
		
		if(value == null || value.isEmpty()) {
			value = message;
		}
		
		return value;
	}

	@Override
	public String getMessage(String messageTemplate, Map<String, Object> properties) {
		return MessageUtil.getMessage(messageTemplate, properties);
	}

	
}
