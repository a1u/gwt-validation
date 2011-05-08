package com.em.validation.client.regex;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public enum RegexProvider {

	INSTANCE;
	
	private RegexProvider(){
		
	}
	
	public boolean matches(String regex, String check) {
		RegExp exp = RegExp.compile(regex);
		MatchResult result = exp.exec(check);
		return result.getIndex() >= 0;
	}
	
}
