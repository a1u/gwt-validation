package com.google.gwt.validation.client.common;

import java.util.Date;
import java.util.Map;

public abstract class PastValidatorAbstract
{

    public boolean isValid(final Object value)
    {
        if (value == null)
            return true;

        boolean isvalid = false;

        if (value instanceof Date)
        {
            Date date = (Date) value;
            isvalid = date.before(new Date());
        }

        return isvalid;
    }

    public void initialize(final Map<String, String> propertyMap)
    {

    }

}
