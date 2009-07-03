package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Pattern</code> annotation.
 * 
 * @author chris
 *
 */
public class PatternValidator implements IConstraint<Pattern> {

	private String pattern;
	
	public void initialize(Pattern constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	public void initialize(Map<String, String> propertyMap) {
	
		/*
		 * !!!!
		 * Notice that these keys are exactly the same as the method names on the annotation
		 * !!!!
		 */
		
		
		this.pattern = propertyMap.get("pattern");		
	}
	
	public boolean isValid(Object value) {
		if(value == null) return true;
		if(this.pattern == null || this.pattern.trim().length() == 0) return true;
		
		boolean valid = false;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			valid = ((String)value).matches(this.pattern);
		} else {
			valid = ((Object)value).toString().matches(this.pattern);
		}

		return valid;
	}



}
