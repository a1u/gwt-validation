package com.google.gwt.validation.client.common;

import java.util.Map;

public abstract class PatternValidatorAbstract {
	protected String pattern;

	public void initialize(Map<String, String> propertyMap) {

		/*
		 * !!!! Notice that these keys are exactly the same as the method names
		 * on the annotation !!!!
		 */

		this.pattern = propertyMap.get("pattern");
	}

	public boolean isValid(Object value) {
		if (value == null)
			return true;
		if (this.pattern == null || this.pattern.trim().length() == 0)
			return true;

		boolean valid = false;

		if (value.getClass().toString().equals(String.class.toString())) {
			valid = ((String) value).matches(this.pattern);
		} else {
			valid = ((Object) value).toString().matches(this.pattern);
		}

		return valid;
	}

}
