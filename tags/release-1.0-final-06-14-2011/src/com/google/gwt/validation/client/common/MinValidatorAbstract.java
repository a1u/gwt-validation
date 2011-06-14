package com.google.gwt.validation.client.common;

import java.util.Map;

public abstract class MinValidatorAbstract
{
    protected int minimum;

    public void initialize(final Map<String, String> propertyMap)
    {
        /*
         * Note that the key is the same as the method name on the constraint
         */
        this.minimum = Integer.parseInt(propertyMap.get("value"));
    }

    public boolean isValid(final Object value)
    {
        if (value == null)
            return true;

        boolean isvalid = false;

        if (value instanceof Integer)
            isvalid = ((Integer) value) >= this.minimum;
        else if (value instanceof Double)
            isvalid = ((Double) value) >= this.minimum;
        else if (value instanceof Float)
            isvalid = ((Float) value) >= this.minimum;
        else if (value instanceof Long)
            isvalid = ((Long) value) >= this.minimum;

        return isvalid;
    }

}
