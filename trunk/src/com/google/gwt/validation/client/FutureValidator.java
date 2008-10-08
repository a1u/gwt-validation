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

import java.util.Date;
import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Validator that implements the <code>@Future</code> annotation
 * 
 * @author chris
 *
 */
public class FutureValidator implements IConstraint<Future> {

    public boolean isValid(Object value) {
        if (value == null) return true;

        boolean isvalid = false;
        
        try {
        	Date date = (Date)value;
        	isvalid = date.after(new Date());        	
        } catch (Exception ex) {
        	
        }
        
        return isvalid;
    }

    public void initialize(Future parameters) {

    }

	public void initialize(Map<String, String> propertyMap) {
		
	}

}
