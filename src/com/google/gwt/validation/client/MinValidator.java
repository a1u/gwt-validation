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
 * Implements the <code>@Min</code> annotation.
 * 
 * @author chris
 *
 */
public class MinValidator implements IConstraint<Min> {

	public interface Min {
	    String[] groups();
        String message();
        int minimum();
	}
	
	private int minimum;
	
	/** {@inheritDoc} */
    public void initialize(final com.google.gwt.validation.client.Min constraintAnnotation) {
        this.minimum = constraintAnnotation.minimum();
    }

	public void initialize(final Min constraintAnnotation) {
		this.minimum = constraintAnnotation.minimum();
	}

    public boolean isValid(final Object value) {
		if(value == null) return true;
		
		boolean isvalid = false;
		
		if(value.getClass().toString().equals(Integer.class.toString())) {
			isvalid = ((Integer)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Double.class.toString())) {
			isvalid = ((Double)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Float.class.toString())) {
			isvalid = ((Float)value) >= this.minimum;
		} else if (value.getClass().toString().equals(Long.class.toString())) {
			isvalid = ((Long)value) >= this.minimum;
		}		
		
		return isvalid;
	}

}
