package com.em.validation.client.regex;

public enum RegexProvider {

	INSTANCE;
	
	private RegexProvider(){
		
	}
	
	public boolean matches(String regex, String check) {
		return true;
	}
	
}
