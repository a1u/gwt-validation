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

import java.util.Collection;
import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * 
 * Validator that implements the <code>@Size</code> annotation.
 * 
 * @author chris
 *
 */
public class SizeValidator implements IConstraint<Size> {

	private int minimum;
	private int maximum;
	
	public void initialize(Size constraintAnnotation) {
		this.minimum = constraintAnnotation.minimum();
		this.maximum = constraintAnnotation.maximum();
	}

	public void initialize(Map<String, String> propertyMap) {
	
		/*
		 * !!!!
		 * Notice that these keys are exactly the same as the method names on the annotation
		 * !!!!
		 */
		
		
		this.minimum = Integer.parseInt(propertyMap.get("minimum"));
		this.maximum = Integer.parseInt(propertyMap.get("maximum"));
		
	}
	
	public boolean isValid(Object value) {
		if(value == null) return true;
		
		boolean valid = false;
		
		int size = -1;
		
		if(size < 0) {
			
			try {
				size = ((Object[])value).length;
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		
		}

		if(size < 0) {
			
			try {
				size = ((int[])value).length;
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		
		}
		
		if(size < 0) {
			
			try {
				size = ((float[])value).length;
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		
		}
		
		if(size < 0) {
			
			try {
				size = ((double[])value).length;
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		
		}
		
		if(size < 0) {
			
			try {
				size = ((long[])value).length;
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		
		}
		
		if(size < 0) {
			
			try {
				size = ((Collection<?>)value).size();
			} catch (Exception ex) {
				
			}

		}
		
		if(size < 0) {
			
			try {
				size = ((Map<?,?>)value).size();
			} catch (Exception ex) {
				
			}

		}

		//no size found
		if(size == -1) return true;
		
		//else compute value
		if(size >= 0 && this.maximum >= size && this.minimum <= size) valid = true;
		
		return valid;
	}



}
