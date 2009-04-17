package com.google.gwt.validation.client;

/*
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
 * Validator that implements the <code>@AssertFalse</code> annotation
 * 
 * @author chris
 *
 */
public class AssertFalseValidator implements IConstraint<AssertFalse> {

    public interface AssertFalse {
        String[] groups();
        String message();
    }

    public void initialize(final AssertFalse constraintAnnotation) {
        
    }
    
    /** {@inheritDoc} */
    public void initialize(final com.google.gwt.validation.client.AssertFalse constraintAnnotation) {
        // TODO Auto-generated method stub
        
    }

    public boolean isValid(final Object value) {
        if (value == null) return true;
        
        boolean isvalid = false;
        
        try {
        	isvalid = !(Boolean)value;
        } catch (final Exception ex) {
        	//isn't boolean / can't be casted as such
        }

        return isvalid;
    }

}
