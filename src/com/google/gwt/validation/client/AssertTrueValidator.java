package com.google.gwt.validation.client;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Validator that implements the <code>@AssertTrue</code> annotation
 * 
 * @author chris
 *
 */
public class AssertTrueValidator implements IConstraint<AssertTrue> {

    public boolean isValid(Object value) {
        if (value == null) return true;
        
        boolean isvalid = false;
        
        try {
        	isvalid = (Boolean)value;
        } catch (Exception ex) {
        	//isn't boolean / can't be casted as such
        }
        
        return isvalid;
    }

    public void initialize(AssertTrue parameters) {

    }

	public void initialize(Map<String, String> propertyMap) {
		
	}

}
