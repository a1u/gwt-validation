package com.google.gwt.validation.client.interfaces;

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

/**
* Describe a constraint validation defect
* <br/>
* TODO add pointers to the metadata?
* <br/>
* From the JSR-303 specification
*
* @author Emmanuel Bernard
*/
public interface IInvalidConstraint<T> {
	/**
	* Error message
	*/
	String getMessage();
	
	/**
	* Root bean being validated
	*/ T getInvalidObject();
	
	/**
	* Bean type being validated
	*/
	//Class<?> getInvalidObjectClass();
	
	/**
	* The value failing to pass the constraint
	*/
	Object getValue();

	/**
	* the property path to the value from <code>rootBean</code>
	* Null if the value is the rootBean itself
	*/
	String getPropertyPath();

	/**
	* return the list of groups that the triggered constraint applies on and witch also are
	* within the list of groups requested for validation
	* (directly or through a group sequence)
	* TODO: considering removal, if you think it's important, speak up
	*/
	//String[] getGroups();
}

