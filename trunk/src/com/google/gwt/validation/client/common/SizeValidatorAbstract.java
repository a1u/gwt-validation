package com.google.gwt.validation.client.common;

import java.util.Collection;
import java.util.Map;

public abstract class SizeValidatorAbstract
{

    protected int minimum;
    protected int maximum;

    public void initialize(final Map<String, String> propertyMap)
    {

        /*
         * !!!! Notice that these keys are exactly the same as the method names on the annotation !!!!
         */

        this.minimum = Integer.parseInt(getMin(propertyMap));
        this.maximum = Integer.parseInt(getMax(propertyMap));

    }

    private String getMin(final Map<String, String> propertyMap)
    {
        String m = propertyMap.get("minimum");
        if (m == null)
            m = propertyMap.get("min");
        return m;
    }

    private String getMax(final Map<String, String> propertyMap)
    {
        String m = propertyMap.get("maximum");
        if (m == null)
            m = propertyMap.get("max");
        return m;
    }

    public boolean isValid(final Object value)
    {
        if (value == null)
            return true;

        boolean valid = false;

        int size = -1;

        while (size < 0)
        {
            if (value instanceof Object[])
                size = ((Object[]) value).length;
            else if (value instanceof int[])
                size = ((int[]) value).length;
            else if (value instanceof float[])
                size = ((float[]) value).length;
            else if (value instanceof double[])
                size = ((double[]) value).length;
            else if (value instanceof long[])
                size = ((long[]) value).length;
            else if (value instanceof Collection<?>)
                size = ((Collection<?>) value).size();
            else if (value instanceof Map<?, ?>)
                size = ((Map<?, ?>) value).size();
            else if (value instanceof String)
                size = ((String) value).trim().length();
            break;
        }

        // no size found
        if (size == -1)
            return true;

        // else compute value
        if (size >= 0 && this.maximum >= size && this.minimum <= size)
            valid = true;

        return valid;
    }

}