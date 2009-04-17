package com.google.gwt.validation.client.test;

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

public class TestAnnotationConstraint implements IConstraint<TestClassLevelAnnotation> {

    public interface TestClassLevelAnnotation {
        String[] groups();
        String message();
    }
    
	/** {@inheritDoc} */
    public void initialize(final com.google.gwt.validation.client.test.TestClassLevelAnnotation constraintAnnotation) {
        
    }

	public void initialize(final TestClassLevelAnnotation constraintAnnotation) {
		
	}

    public boolean isValid(final Object value) {

		//cannot be null
		if(value == null) return false;
		
		boolean valid = false;
		
		if(!valid) {
			
			try {
			
				final AnnotatedSuperClass ac = (AnnotatedSuperClass)value;
											//under java6 this would use the
											//idiom !.isEmpty() so preserving
											//the same usage of !
				if(ac.getName() != null && !(ac.getName().trim().length() == 0)) {
					valid = true;
				}
				
			} catch (final ClassCastException ccex) {
				//do nothing, invalid still
				//ccex.printStackTrace();
			} catch (final Exception ex) {
				//do nothing, invalid still
				//ex.printStackTrace();
			}
			
		}
		
		return valid;
	}
	
}
