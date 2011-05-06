package com.em.validation.client.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegexProvider {

	INSTANCE;
	
	private RegexProvider(){
		
	}
	
	public boolean matches(String regex, String check) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(check);
		return matcher.matches();
	}
	
}
