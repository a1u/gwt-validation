package com.google.gwt.validation.rebind;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * Rebind helper class that stores all the information about a 
 * method, field, and annotation set so that validation can either
 * occur (as on the server) or so that validation code can be generated
 * (as in the GWT compilation process).
 * 
 * @author chris
 *
 */
public class ValidationPackage
{

    private final static String[] PROPERTIES_PREFIXES =
    { "get", "set", "is" };

    /**
     * Create a validation package
     * 
     */
    private ValidationPackage()
    {

    }

    /**
     * Create a validation package for the given constraint, with a 
     * message specified, for the method and field given that applies
     * to the groups as specified, with the given property map for
     * initilization of the constraint implementation.
     * <br/>
     * The parameter method is only null if it is a class level annotation.
     * 
     * @param constraint the constraint that implements the validation 
     * @param message the message if the constraint fails (returns false on validation)
     * @param method the method to invoke for validation
     * @param field the field that the method maps to (if applicable)
     * @param groups the groups that the validation should be performed under
     * @param propertyMap the properties to be used to initialize the constraint
     */
    public ValidationPackage(final IConstraint<Annotation> constraint, final String message, final Method method,
            final Field field, final String[] groups, final Map<String, String> propertyMap)
    {

        // construct
        this();

        // set values
        this.message = message;
        this.field = field;
        this.method = method;
        this.implementingConstraint = constraint;

        // convert groups[] to arraylist
        this.groups = new ArrayList<String>();
        for (String g : groups)
            this.groups.add(g);
        // add default group if no groups specified
        if (this.groups.size() == 0)
            this.groups.add("default");

        // compute base item name
        if (this.method != null)
        {
            this.itemName = this.method.getName();
            this.isField = false;
            // if field is not a null
            if (this.field != null)
            {
                this.itemName = this.field.getName();
                this.isField = true;
            }
            else
                for (String element : PROPERTIES_PREFIXES)
                    // if it is a method but field is null, we need to check if the method is a getter (is) / setter
                    if (this.itemName.startsWith(element))
                    {
                        // we set the filed property :
                        this.itemName = StringUtils.uncapitalize(this.itemName.substring(element.length()));
                        this.isField = true;
                        break;
                    }
        }
        else
        {
            this.isField = false;
            this.itemName = "this";
        }

        // set property map
        this.validationPropertyMap = propertyMap;
        if (this.validationPropertyMap == null)
            this.validationPropertyMap = new HashMap<String, String>();

    }

    private String message;
    private ArrayList<String> groups;
    private Method method;
    private Field field;
    private String itemName;
    private Map<String, String> validationPropertyMap;
    private IConstraint<Annotation> implementingConstraint;
    private boolean isField;

    /**
     * Build an <code>InvalidConstraint</code> object that matches the annotation
     * @param <T>
     * 
     * @return
     */
    public <T> InvalidConstraint<T> buildInvalidConstraint(final T object)
    {

        InvalidConstraint<T> ic = new InvalidConstraint<T>(this.itemName, this.message);

        // set object
        ic.setInvalidObject(object);

        return ic;
    }

    /**
     * Get an instance of the implementing constraint
     * 
     * @return
     */
    public IConstraint<Annotation> getImplementingConstraint()
    {
        return this.implementingConstraint;
    }

    /**
     * Get the property map
     * 
     * @return
     */
    public Map<String, String> getValidationPropertyMap()
    {
        return this.validationPropertyMap;
    }

    /**
     * Returns true if the package is for a field with a getter method.
     * 
     * @return
     */
    public boolean isFieldBased()
    {
        return this.isField;
    }

    /**
     * Get the <code>java.lang.reflect.Method</code> that represents the
     * method to invoke. 
     * 
     * @return
     */
    public Method getMethod()
    {
        return this.method;
    }

    /**
     * Outward facing item name that represents the field or method to be validated
     * 
     * @return
     */
    public String getItemName()
    {
        return this.itemName;
    }

    /**
     * Get the message that will be used if validation fails
     * 
     * @return
     */
    public String getMessage()
    {
        return this.message;
    }

    @Override
    public String toString()
    {
        return this.itemName;
    }

    /**
     * Get the list of groups that this validation belongs in.
     * 
     * @return
     */
    public ArrayList<String> getGroups()
    {
        return this.groups;
    }
}
