package com.google.gwt.validation.client;

import java.util.Date;
import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Validator that implements the <code>@Past</code> annotation
 * 
 * @author chris
 *
 */
public class PastValidator implements IConstraint<Past> {

    public boolean isValid(Object value) {
        if (value == null) return true;

        boolean isvalid = false;
        
        try {
        	Date date = (Date)value;
        	isvalid = date.before(new Date());        	
        } catch (Exception ex) {
        	
        }
        
        return isvalid;
    }

    public void initialize(Past parameters) {

    }

	public void initialize(Map<String, String> propertyMap) {
		
	}

}
