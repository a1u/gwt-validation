package com.em.validation.rebind.generator.source;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.scan.ClassScanner;
import com.em.validation.rebind.template.TemplateController;

public enum MessageGenerator {

	INSTANCE;

	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.message";
	private final String CLASS_NAME = "GeneratedMessage";
	
	public class LocaleItem {
		
		public String code = "";
		public String key = "";
		public String value = "";
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
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
	
	public ClassDescriptor generateMessage() {
		
		ClassDescriptor descriptor = new ClassDescriptor();
		descriptor.setClassName(this.CLASS_NAME);
		descriptor.setFullClassName(this.TARGET_PACKAGE + "." + descriptor.getClassName());
		descriptor.setPackageName(this.TARGET_PACKAGE);

		//create locale list
		List<LocaleItem> locales = new ArrayList<LocaleItem>();
		
		//loop through locales
		Set<String> resources = ClassScanner.INSTANCE.getLocaleResources();
		for(String resource : resources) {
		
			if(resource == null || !resource.contains("ValidationMessages") || !resource.contains(".properties")) continue;
			
			//get locale
			String localeKey = resource;
			if(localeKey != null && localeKey.length() >= 7 && localeKey.toLowerCase().contains(".properties")) {

				localeKey = localeKey.substring(localeKey.lastIndexOf("ValidationMessages")+18,localeKey.lastIndexOf(".properties")).toUpperCase();
				if(localeKey.startsWith("_")) {
					localeKey = localeKey.substring(1);
				}
				
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
						continue;
					}
				}
				
				if(bundle != null) {
					Enumeration<String> keys = bundle.getKeys();
					while(keys.hasMoreElements()) {
						String key = keys.nextElement();
						if(key != null) {
							String value = bundle.getString(key);
							LocaleItem item = new LocaleItem();
							item.setCode(localeKey);
							item.setKey(key.toString());
							item.setValue(value);
							
							locales.add(item);
						}
					}
				}
			}				
		}
				
		//create template map
		Map<String,Object> messageModel = new HashMap<String, Object>();
		messageModel.put("targetPackage", this.TARGET_PACKAGE);
		messageModel.put("className", this.CLASS_NAME);
		messageModel.put("locales", locales);
		
		//do template
		descriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/message/GeneratedMessage.ftl", messageModel));
		
		return descriptor;
	}
	
}
