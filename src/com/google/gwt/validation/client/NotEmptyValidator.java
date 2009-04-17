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
 * Implements the <code>@NotEmpty</code> annotation.
 * 
 * @author chris
 *
 */
public class NotEmptyValidator implements IConstraint<NotEmpty> {

	public interface NotEmpty {
	    String[] groups();
        String message();
	}
	
	/** {@inheritDoc} */
    public void initialize(final com.google.gwt.validation.client.NotEmpty constraintAnnotation) {
        
    }
	
	public void initialize(final NotEmpty constraintAnnotation) {
				
	}

    public boolean isValid(final Object value) {
		//per discussion with JSR-303 specification members
		if(value == null) return false;
		
		boolean valid = false;
		
		int size = -1;
		
		if(value.getClass().toString().equals(String.class.toString())) {
			size = ((String)value).trim().length();
		}

		if(size > 0) valid = true;
		
		return valid;
	}



}
