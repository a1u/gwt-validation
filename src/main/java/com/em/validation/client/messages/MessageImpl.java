package com.em.validation.client.messages;

import java.util.HashMap;
import java.util.Map;

public abstract class MessageImpl implements IMessage {

	private Map<String, String> localeMap = new HashMap<String, String>();
	
	protected void addString(String key, String value) {
		//put key and value into locale map
		this.localeMap.put(key,value);
	}
	
	@Override
	public String getString(String key) {
		return this.localeMap.get(key);
	}	
}
