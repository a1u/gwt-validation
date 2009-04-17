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

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * 
 * Validator that implements the <code>@Length</code> annotation.
 * 
 * @author chris
 *
 */
public class LengthValidator implements IConstraint<Length> {
    
    
    public interface Length {
        String[] groups();
        String message();
        int maximum();
        int minimum();
    }

	private int minimum;
	private int maximum;
	
	public void initialize(final Length constraintAnnotation) {
		this.minimum = constraintAnnotation.minimum();
		this.maximum = constraintAnnotation.maximum();
	}
	
	public void initialize(final com.google.gwt.validation.client.Length constraintAnnotation) {
        this.minimum = constraintAnnotation.minimum();
        this.maximum = constraintAnnotation.maximum();
    }
	
	public boolean isValid(final Object value) {
		if(value == null) return true;
		
		boolean valid = false;
		
		int size = -1;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			size = ((String)value).trim().length();
		}

		if(size >= 0 && this.maximum >= size && this.minimum <= size) valid = true;
		
		return valid;
	}

}
