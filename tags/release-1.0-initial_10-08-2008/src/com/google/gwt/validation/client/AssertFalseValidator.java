package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Validator that implements the <code>@AssertFalse</code> annotation
 * 
 * @author chris
 *
 */
public class AssertFalseValidator implements IConstraint<AssertFalse> {

    public boolean isValid(Object value) {
        if (value == null) return true;
        
        boolean isvalid = false;
        
        try {
        	isvalid = !(Boolean)value;
        } catch (Exception ex) {
        	//isn't boolean / can't be casted as such
        }

        return isvalid;
    }

    public void initialize(AssertFalse parameters) {

    }

	public void initialize(Map<String, String> propertyMap) {
		
	}

}
