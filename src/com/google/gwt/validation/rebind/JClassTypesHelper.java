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
package com.google.gwt.validation.rebind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;

/**
 * Help to operate on JClassTypes. It can be used with GWT generator's context. 
 * 
 * <p>
 * Created on 2009-04-09
 * 
 * @author kardigen
 * @version $Id$
 */
public class JClassTypesHelper {

    /**
     * Get all related classes. It return set of all superclasses in hierarchy of specified class, 
     * all subclasses that inherit specified class, all implemented interfaces and all interfaces that are extended.
     * 
     * @param clazz start point class.
     * @return set of related classes.
     */
    public static Set<JClassType> getRelatedClasses(final JClassType clazz) {
        final Set<JClassType> result = new HashSet<JClassType>();
        //add class
        result.add(clazz);
        //add subclasses 
        result.addAll(getSubtypes(clazz));
        //add superclases 
        result.addAll(getSupertypes(clazz));
        //add interfaces
        result.addAll(getRelatedInterfaces(result));
        return result;
    }

    /** Get all related interfaces. Returns set of all implemented interfaces of classes.
     * @param classes.
     * @return set of interfaces.
     */
    private static Set<JClassType> getRelatedInterfaces(final Set<JClassType> classes) {
        // prepare result set
        final HashSet<JClassType> result = new HashSet<JClassType>();

        // get all implemented interfaces
        for (final JClassType clazz : classes) {
            result.addAll(Arrays.asList(clazz.getImplementedInterfaces()));
        }

        // get supertypes for interfaces
        if (!result.isEmpty()) {
            result.addAll(getRelatedInterfaces(result));
        }

        return result;
    }

    /** Get all subtypes that extends specified class.
     * @param class.
     * @return set of classes.
     */
    private static Set<JClassType> getSubtypes(final JClassType clazz) {

        // prepare result set
        final HashSet<JClassType> result = new HashSet<JClassType>();

        // get subtypes
        final JClassType[] subTypes = clazz.getSubtypes();

        // add all subtypes to result set
        result.addAll(Arrays.asList(subTypes));

        // get subtypes for each subtype
        for (final JClassType classType : subTypes) {
            result.addAll(getSubtypes(classType));
        }

        return result;
    }

    /** Get all super types in hierarchy.
     * @param clazz
     * @return set of superclasses.
     */
    private static Set<JClassType> getSupertypes(final JClassType clazz) {

        // get super class
        final JClassType superclass = clazz.getSuperclass();

        // prepare result set
        final Set<JClassType> result = new HashSet<JClassType>();

        // go deeper in hierarchy to Object or null
        if (superclass != null && !superclass.getQualifiedSourceName().equals(Object.class.getName())) {
            //add super class to result set
            result.add(superclass);
            //add descendants 
            result.addAll(getSupertypes(superclass));
        }
        
        return result;
    }

}
