package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Email</code> annotation.
 * 
 * @author chris
 *
 */
public class EmailValidator implements IConstraint<Email> {

	public void initialize(Email constraintAnnotation) {

	}

	public void initialize(Map<String, String> propertyMap) {

	}
	
	public boolean isValid(Object value) {
		if(value == null) return true;
		
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";
		
		boolean valid = false;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			valid = ((String)value).matches(emailPattern);
		} else {
			valid = ((Object)value).toString().matches(emailPattern);
		}

		return valid;
	}



}
