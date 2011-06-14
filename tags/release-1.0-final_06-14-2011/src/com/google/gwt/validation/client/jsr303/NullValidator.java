package com.google.gwt.validation.client.jsr303;

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

import javax.validation.constraints.Null;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Implements the <code>@Null</code> annotation.
 * 
 * @author chris
 *
 */
public class NullValidator implements IConstraint<Null> {


    public void initialize(Null parameters) {
    	//nothing
    }

	@Override
	public void initialize(Map<String, String> propertyMap) {
		//nothing
	}

	@Override
	public boolean isValid(Object value) {
		return (value == null);
	}


}