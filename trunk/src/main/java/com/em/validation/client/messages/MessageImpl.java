package com.em.validation.client.messages;

import java.util.HashMap;
import java.util.Map;

public abstract class MessageImpl implements IMessage {

	private Map<String, Map<String,String>> localeMap = new HashMap<String, Map<String,String>>();
	
	protected void addString(String localeCode, String key, String value) {
		//locale code is uppercase, best match and stuff
		if(localeCode != null) {
			localeCode = localeCode.toUpperCase();		
		} else {
			localeCode = "";
		}
		
		//get map
		Map<String,String> mapForCode = this.localeMap.get(localeCode);
		if(mapForCode == null) {
			mapForCode = new HashMap<String, String>();
			this.localeMap.put(localeCode, mapForCode);
		}
		
		//put key and value into locale map
		mapForCode.put(key,value);
	}
	
	@Override
	public String getString(String localeCode, String key) {
		//locale code is upper case, best match and stuff
		if(localeCode != null) {
			localeCode = localeCode.toUpperCase();		
		} else {
			localeCode = "";
		}
		
		//value
		String value = null;
		
		//get map
		Map<String,String> mapForCode = this.localeMap.get(localeCode);
		if(mapForCode != null) {
			value = mapForCode.get(key);
		}
		
		if(value == null && !"".equals(localeCode)) {
			return this.getString("", key);
		}
		
		return value;
	}	
}
