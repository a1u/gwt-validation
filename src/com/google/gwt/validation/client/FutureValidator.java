package com.google.gwt.validation.client;

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
