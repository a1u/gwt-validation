package com.em.validation.rebind.generator.source;

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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.template.TemplateController;
import com.google.gwt.i18n.shared.GwtLocale;

public enum MessageGenerator {

	INSTANCE;

	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.message";
	private final String CLASS_NAME = "GeneratedMessage";
	
	public static class LocaleItem {
		
		public String key = "";
		public String value = "";
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}		
	}
	
	public ClassDescriptor generateMessage(GwtLocale current) {
		
		ClassDescriptor descriptor = new ClassDescriptor();
		String localClassName = this.CLASS_NAME;
		if(!current.getAsString().isEmpty()) {
			localClassName = this.CLASS_NAME + "_" + current.getAsString();
		}
		descriptor.setClassName(localClassName);
		descriptor.setFullClassName(this.TARGET_PACKAGE + "." + descriptor.getClassName());
		descriptor.setPackageName(this.TARGET_PACKAGE);

		//create locale list
		List<LocaleItem> locales = new ArrayList<LocaleItem>();
		
		String localeKey = current.getAsString().toUpperCase();
		
		//create locale
		Locale locale = new Locale(localeKey.toLowerCase());
		
		//lookup resource bundle
		ResourceBundle bundle = null;
		try { 
			bundle = ResourceBundle.getBundle("ValidationMessages", locale);
		} catch (Exception e) {
			try {
				//get from default locale
				bundle = ResourceBundle.getBundle("ValidationMessages");
			} catch (Exception ex) {

			}
		}
		
		if(bundle != null) {
			Enumeration<String> keys = bundle.getKeys();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				if(key != null) {
					String value = bundle.getString(key);
					LocaleItem item = new LocaleItem();
					item.setKey(key);
					item.setValue(value);
					
					locales.add(item);
				}
			}
		}
				
		//create template map
		Map<String,Object> messageModel = new HashMap<String, Object>();
		messageModel.put("targetPackage", descriptor.getPackageName());
		messageModel.put("className", descriptor.getClassName());
		messageModel.put("locales", locales);
		
		//do template
		descriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/message/GeneratedMessage.ftl", messageModel));
		
		return descriptor;
	}
	
}
