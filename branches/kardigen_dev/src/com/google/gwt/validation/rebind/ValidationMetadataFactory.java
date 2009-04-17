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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.validation.client.ConstraintValidator;
import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.GroupSequences;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IConstraint;

/**
 * 
 * <p>
 * Created on 2009-04-11
 *
 * @author kardigen
 * @version $Id$
 */
public class ValidationMetadataFactory {

    /**
     * Returns a list of validators that apply to each method of a given class
     * (and only this class), that are given for the valid list of
     * groups and that are in order by group ordering as annotated in the class.
     * All field validators are converted to the appropriate getter, if one
     * exists. Only 0 argument methods are considered for constraint validation.
     * 
     * 
     * @param className
     * @return List of validation packages.
     */
    public static ArrayList<ValidationPackage> getValidatorsForClass(final Class<?> inputClass) {

        // bail if problem with input class
        if (inputClass == null) {
            return new ArrayList<ValidationPackage>();
        }

        // holder hashmap for returnset
        final ArrayList<ValidationPackage> validationPackageList = new ArrayList<ValidationPackage>();

        // map method names to (valid) annotations (those that contain a
        // ConstraintValidator)
        final HashMap<Method, ArrayList<Annotation>> annotationMapping = new HashMap<Method, ArrayList<Annotation>>();

        // use arraylist to keep them in order
        final ArrayList<Method> methodOrder = new ArrayList<Method>();

        // process fields (looking for annotations for subclasses as well)
        for (final Field field : ValidationMetadataFactory.getAllFields(inputClass)) {

            // convert field to method
            final Method fieldGetter = ValidationMetadataFactory.convertFieldToGetter(inputClass, field);

            // if the getter is not null, proceed with the rest of the method
            if (fieldGetter != null) {

                // get list of annotations on the field
                for (final Annotation annotationOnField : field.getAnnotations()) {

                    // annotation array
                    Annotation[] annotationArray = new Annotation[] {};

                    // annotation array (for determining if there are
                    // validations withing the value of an annotation)
                    try {
                        final Method valueOnAnnotation = annotationOnField.annotationType().getDeclaredMethod("value");
                        if (valueOnAnnotation != null) {
                            annotationArray = (Annotation[]) valueOnAnnotation.invoke(annotationOnField, new Object[] {});
                        }
                    } catch (final SecurityException e) {
                        // e.printStackTrace();
                    } catch (final NoSuchMethodException e) {
                        // e.printStackTrace();
                    } catch (final Exception ex) {
                        // ex.printStackTrace();
                    }

                    // if the value method is an array of annotations
                    // treat them as if they were all annotations on
                    // the given method
                    if (annotationArray.length > 0) {

                        // process each annotation element in the array found on
                        // .value()
                        for (final Annotation annotationElement : annotationArray) {

                            // find a ConstraintValidator annotation on the
                            // given annotation type (for the annotation on the
                            // field)!
                            final Annotation checkTypeForConstraintValidator = annotationElement.annotationType().getAnnotation(ConstraintValidator.class);

                            // get the arraylist of annotations for the method
                            // name
                            ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(fieldGetter);
                            if (listOfAnnotationsForGivenMethod == null)
                                listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();

                            // if the annotation is not null, add the annotation
                            // on field to the annotation mapping for the given
                            // method name
                            if (checkTypeForConstraintValidator != null) {

                                // get groups method
                                listOfAnnotationsForGivenMethod.add(annotationElement);
                            }

                            // if the size of the list of annotations is > 0
                            // save the arraylist back to the mapping
                            if (listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
                                annotationMapping.put(fieldGetter, listOfAnnotationsForGivenMethod);

                                // add to ordering list
                                if (!methodOrder.contains(fieldGetter))
                                    methodOrder.add(fieldGetter);
                            }

                        }

                        // otherwise process as normal
                    } else {

                        // find a ConstraintValidator annotation on the given
                        // annotation type (for the annotation on the field)!
                        final Annotation checkTypeForConstraintValidator = annotationOnField.annotationType().getAnnotation(ConstraintValidator.class);

                        // get the arraylist of annotations for the method name
                        ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(fieldGetter);
                        if (listOfAnnotationsForGivenMethod == null) {
                            listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
                        }

                        // if the annotation is not null, add the annotation on
                        // field to the annotation mapping for the given method
                        // name
                        if (checkTypeForConstraintValidator != null) {

                            // get groups method
                            listOfAnnotationsForGivenMethod.add(annotationOnField);
                        }

                        // if the size of the list of annotations is > 0 save
                        // the arraylist back to the mapping
                        if (listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
                            annotationMapping.put(fieldGetter, listOfAnnotationsForGivenMethod);

                            // add to ordering list
                            if (!methodOrder.contains(fieldGetter))
                                methodOrder.add(fieldGetter);
                        }

                    }
                }

            } else {
                // skip field because it doesn't have a valid getter
            }

        }

        // process methods (looking for annotations on subclasses as well)
        for (final Method method : ValidationMetadataFactory.getAllMethods(inputClass)) {

            // get integer modifier
            final int methodModifiers = method.getModifiers();

            // methods must not already be in the mapping and must have 0
            // arguments (and must be accessible)
            if (!annotationMapping.containsKey(method) && method.getParameterTypes().length == 0 && !Modifier.isPrivate(methodModifiers) && !Modifier.isProtected(methodModifiers)) {

                // get list of annotations on the field
                for (final Annotation annotationOnMethod : method.getAnnotations()) {

                    // annotation array (for determining if there are
                    // validations withing the value of an annotation)
                    Annotation[] annotationArray = new Annotation[] {};

                    // try and find a .value() on the annotation
                    try {
                        final Method valueOnAnnotation = annotationOnMethod.annotationType().getDeclaredMethod("value");
                        if (valueOnAnnotation != null) {
                            annotationArray = (Annotation[]) valueOnAnnotation.invoke(annotationOnMethod, new Object[] {});
                        }
                    } catch (final SecurityException e) {
                        // e.printStackTrace();
                    } catch (final NoSuchMethodException e) {
                        // e.printStackTrace();
                    } catch (final Exception ex) {
                        // ex.printStackTrace();
                    }

                    // if the value method is an array of annotations
                    // treat them as if they were all annotations on
                    // the given method
                    if (annotationArray.length > 0) {

                        // process each annotation element in the array found on
                        // .value()
                        for (final Annotation annotatedElement : annotationArray) {

                            // find a ConstraintValidator annotation on the
                            // given annotation type (for the annotation on the
                            // field)!
                            final Annotation checkTypeForConstraintValidator = annotatedElement.annotationType().getAnnotation(ConstraintValidator.class);

                            // get the arraylist of annotations for the method
                            // name
                            ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(method);
                            if (listOfAnnotationsForGivenMethod == null) {
                                listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
                            }

                            // if the annotation is not null, add the annotation
                            // on field to the annotation mapping for the given
                            // method name
                            if (checkTypeForConstraintValidator != null) {
                                listOfAnnotationsForGivenMethod.add(annotatedElement);
                            }

                            // if the size of the list of annotations is > 0
                            // save the arraylist back to the mapping
                            if (listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
                                annotationMapping.put(method, listOfAnnotationsForGivenMethod);

                                // add to ordering list
                                if (!methodOrder.contains(method))
                                    methodOrder.add(method);
                            }
                        }
                        // otherwise continue processing the annotation as
                        // normal
                    } else {

                        // find a ConstraintValidator annotation on the given
                        // annotation type (for the annotation on the field)!
                        final Annotation checkTypeForConstraintValidator = annotationOnMethod.annotationType().getAnnotation(ConstraintValidator.class);

                        // get the arraylist of annotations for the method name
                        ArrayList<Annotation> listOfAnnotationsForGivenMethod = annotationMapping.get(method);
                        if (listOfAnnotationsForGivenMethod == null) {
                            listOfAnnotationsForGivenMethod = new ArrayList<Annotation>();
                        }

                        // if the annotation is not null, add the annotation on
                        // field to the annotation mapping for the given method
                        // name
                        if (checkTypeForConstraintValidator != null) {
                            listOfAnnotationsForGivenMethod.add(annotationOnMethod);
                        }

                        // if the size of the list of annotations is > 0 save
                        // the arraylist back to the mapping
                        if (listOfAnnotationsForGivenMethod != null && listOfAnnotationsForGivenMethod.size() > 0) {
                            annotationMapping.put(method, listOfAnnotationsForGivenMethod);

                            // add to ordering list
                            if (!methodOrder.contains(method))
                                methodOrder.add(method);
                        }
                    }
                }
            }

        }

        // build validation packages now that we have a list of the annotations
        // that belong to a given method or method/field pair
        for (final Method method : methodOrder) {

            // double check method modifiers
            final int modifiers = method.getModifiers();

            // make sure method is okay and good to go for a later call to
            // object.method() with 0 args and a return type != void/null
            if (annotationMapping.get(method).size() > 0 && !method.getReturnType().equals(void.class) && !Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers)) {

                // get matching field, if one exists, for later use
                final Field field = ValidationMetadataFactory.extrapolateFieldForGetter(inputClass, method);

                // get list for method
                final ArrayList<Annotation> annotationListForMethod = annotationMapping.get(method);

                // protect from stupid
                if (annotationListForMethod != null && annotationListForMethod.size() > 0) {

                    // process annotations
                    for (final Annotation annotation : annotationListForMethod) {

                        // get ConstraintValidator
                        ConstraintValidator cv = null;
                        IConstraint<Annotation> constraint = null;

                        // get cv and constraint
                        try {
                            cv = annotation.annotationType().getAnnotation(ConstraintValidator.class);
                            final Method valueMethod = cv.annotationType().getMethod("value");
                            final Class<?> validatorClass = (Class<?>) valueMethod.invoke(cv);

                            // suppress warnings because at this time we can be
                            // reasonably sure that it is of
                            // type IConstraint<Annotation> and that it does
                            // extend object. I hate to rely on the "magic"
                            // of trusting the compiler but for some of this
                            // stuff it has become imperative.
                            @SuppressWarnings("unchecked")
                            final IConstraint<Annotation> constraintFound = (IConstraint<Annotation>) validatorClass.getDeclaredConstructor().newInstance(new Object[] {});

                            if (constraintFound != null) {
                                constraint = constraintFound;
                            }

                        } catch (final SecurityException e) {
                            // e.printStackTrace();
                        } catch (final IllegalArgumentException e) {
                            // e.printStackTrace();
                        } catch (final IllegalAccessException e) {
                            // e.printStackTrace();
                        } catch (final InvocationTargetException e) {
                            // e.printStackTrace();
                        } catch (final NoSuchMethodException e) {
                            // e.printStackTrace();
                        } catch (final InstantiationException e) {
                            // e.printStackTrace();
                        }

                        // group and message holders
                        String[] groups = new String[] {};
                        String message = "";

                        // get groups and message
                        try {
                            final Method groupMethod = annotation.annotationType().getDeclaredMethod("groups");
                            groups = (String[]) groupMethod.invoke(annotation, new Object[] {});
                            final Method messageMethod = annotation.annotationType().getDeclaredMethod("message");
                            message = (String) messageMethod.invoke(annotation, new Object[] {});
                        } catch (final SecurityException e) {
                            // e.printStackTrace();
                        } catch (final NoSuchMethodException e) {
                            // e.printStackTrace();
                        } catch (final IllegalArgumentException e) {
                            // e.printStackTrace();
                        } catch (final IllegalAccessException e) {
                            // e.printStackTrace();
                        } catch (final InvocationTargetException e) {
                            // e.printStackTrace();
                        }
                        
                        // we need to have a constraint for this to finish and
                        // make the appropriate package
                        if (constraint != null) {

                            // initialize constraint with given annotation
                            constraint.initialize(annotation);

                            // build the validation package
                            final ValidationPackage vPackage = new ValidationPackage(constraint, message, method, field, groups, annotation);

                            // a constraint was found, so add to validation list
                            // thing
                            validationPackageList.add(vPackage);
                        }

                        // all annotations processed
                    }

                    // that method had annotations
                }

                // that method was in the method map (paranoid check)
            }

            // all methods done and all packages created
        }

        return validationPackageList;
    }

    public static ArrayList<ValidationPackage> getClassLevelValidatorsForClass(final Class<?> inputClass) {

        // breakout if input is invalid
        if (inputClass == null || inputClass.equals(Object.class))
            return new ArrayList<ValidationPackage>();

        // create empty array list
        final ArrayList<ValidationPackage> classValidationPackageList = new ArrayList<ValidationPackage>();

        // get annotations
        final Annotation[] classLevelAnnotationlist = inputClass.getAnnotations();

        // process annotations
        for (final Annotation classAnnotation : classLevelAnnotationlist) {

            IConstraint<Annotation> constraint = null;

            try {

                final ConstraintValidator cv = classAnnotation.annotationType().getAnnotation(ConstraintValidator.class);
                Method valueMethod;
                valueMethod = cv.annotationType().getMethod("value");

                final Class<?> validatorClass = (Class<?>) valueMethod.invoke(cv);

                // suppress warnings because at this time we can be reasonably
                // sure that it is of
                // type IConstraint<Annotation> and that it does extend object.
                // I hate to rely on the "magic"
                // of trusting the compiler but for some of this stuff it has
                // become imperative.
                @SuppressWarnings("unchecked")
                final IConstraint<Annotation> constraintFound = (IConstraint<Annotation>) validatorClass.getDeclaredConstructor().newInstance(new Object[] {});

                if (constraintFound != null) {
                    constraint = constraintFound;
                }

            } catch (final SecurityException e) {
                // e.printStackTrace();
            } catch (final NoSuchMethodException e) {
                // e.printStackTrace();
            } catch (final IllegalArgumentException e) {
                // e.printStackTrace();
            } catch (final IllegalAccessException e) {
                // e.printStackTrace();
            } catch (final InvocationTargetException e) {
                // e.printStackTrace();
            } catch (final InstantiationException e) {
                // e.printStackTrace();
            } catch (final NullPointerException npe) {
                // not a proper constraint annotation for what we're looking for
            }

            // if a constraint was found, build validation package
            if (constraint != null) {

                // initialize constraint
                constraint.initialize(classAnnotation);

                String[] groups = new String[] {};
                String message = "";
                // get groups and messages
                try {
                    final Method groupMethod = classAnnotation.annotationType().getDeclaredMethod("groups");
                    groups = (String[]) groupMethod.invoke(classAnnotation, new Object[] {});
                    final Method messageMethod = classAnnotation.annotationType().getDeclaredMethod("message");
                    message = (String) messageMethod.invoke(classAnnotation, new Object[] {});
                } catch (final SecurityException e) {
                    // e.printStackTrace();
                } catch (final NoSuchMethodException e) {
                    // e.printStackTrace();
                } catch (final IllegalArgumentException e) {
                    // e.printStackTrace();
                } catch (final IllegalAccessException e) {
                    // e.printStackTrace();
                } catch (final InvocationTargetException e) {
                    // e.printStackTrace();
                }
                

                // build validation package
                final ValidationPackage vp = new ValidationPackage(constraint, message, null, null, groups,classAnnotation);

                // add validation package to list
                classValidationPackageList.add(vp);
            }

        }

        return classValidationPackageList;
    }

    public static ArrayList<ValidationPackage> getValidAnnotedPackages(final Class<?> inputClass) {
        // create empty array
        final ArrayList<ValidationPackage> validAnnotationPackageList = new ArrayList<ValidationPackage>();

        // if the input class is null
        if (inputClass == null) {
            return validAnnotationPackageList;
        }

        // use arraylist to keep them in order
        final ArrayList<Method> methodOrder = new ArrayList<Method>();

        // process fields (looking for annotations for subclasses as well)
        for (final Field field : ValidationMetadataFactory.getAllFields(inputClass)) {

            // convert field to method
            final Method fieldGetter = ValidationMetadataFactory.convertFieldToGetter(inputClass, field);

            // if the getter is not null, proceed with the rest of the method
            if (fieldGetter != null) {

                // find a @Valid annotation on the given annotation type (for
                // the annotation on the field)!
                final Annotation checkForValid = field.getAnnotation(Valid.class);

                // if the annotation is not null, add the annotation on field to
                // the annotation mapping for the given method name
                if (checkForValid != null) {

                    // get groups method
                    if (!methodOrder.contains(fieldGetter))
                        methodOrder.add(fieldGetter);
                }

            } else {
                // skip field because it doesn't have a valid getter
            }

        }

        // process methods (looking for annotations on subclasses as well)
        for (final Method method : ValidationMetadataFactory.getAllMethods(inputClass)) {

            // get integer modifier
            final int methodModifiers = method.getModifiers();

            // methods must not already be in the mapping and must have 0
            // arguments (and must be accessible)
            if (!methodOrder.contains(method) && method.getParameterTypes().length == 0 && !Modifier.isPrivate(methodModifiers) && !Modifier.isProtected(methodModifiers)) {

                // find a @Valid annotation on the given annotation type (for
                // the annotation on the field)!
                final Annotation checkForValid = method.getAnnotation(Valid.class);

                // if the annotation is not null, add the annotation on field to
                // the annotation mapping for the given method name
                if (checkForValid != null) {

                    // get groups method
                    if (!methodOrder.contains(method))
                        methodOrder.add(method);
                }
            }

        }

        // build validation packages now that we have a list of the annotations
        // that belong to a given method or method/field pair
        for (final Method method : methodOrder) {

            // double check method modifiers
            final int modifiers = method.getModifiers();

            // make sure method is okay and good to go for a later call to
            // object.method() with 0 args and a return type != void/null
            if (!method.getReturnType().equals(void.class) && !Modifier.isPrivate(modifiers) && !Modifier.isProtected(modifiers)) {

                // get matching field, if one exists, for later use
                final Field field = ValidationMetadataFactory.extrapolateFieldForGetter(inputClass, method);

                // group holder
                String[] groups = new String[] {};

                // get valid annotation
                Annotation annotation = method.getAnnotation(Valid.class);
                if (annotation == null) {
                    annotation = field.getAnnotation(Valid.class);
                }

                if (annotation != null) {

                    // get groups
                    try {
                        final Method groupMethod = annotation.annotationType().getDeclaredMethod("groups");
                        groups = (String[]) groupMethod.invoke(annotation, new Object[] {});
                    } catch (final SecurityException e) {
                        // e.printStackTrace();
                    } catch (final NoSuchMethodException e) {
                        // e.printStackTrace();
                    } catch (final IllegalArgumentException e) {
                        // e.printStackTrace();
                    } catch (final IllegalAccessException e) {
                        // e.printStackTrace();
                    } catch (final InvocationTargetException e) {
                        // e.printStackTrace();
                    }

                    // create package and add
                    final ValidationPackage vp = new ValidationPackage(null, "{message.cascadingvalidator}", method, field, groups, null);

                    // add validation package to list
                    validAnnotationPackageList.add(vp);
                }

            }

            // all methods done and all packages created
        }

        // return this list of annoated packages
        return validAnnotationPackageList;
    }

    /**
     * Attempts to find a getter for the given field
     * 
     * @param inputClass
     * @param inputField
     * @return
     */
    private static Method convertFieldToGetter(final Class<?> inputClass, final Field inputField) {

        // get field name
        final String name = inputField.getName();

        // chop off first char
        String getterName = name.substring(1);

        // get first char
        final String first = name.charAt(0) + "";

        // first is uppercase
        if (first.equals(first.toUpperCase())) {
            getterName = first.toLowerCase() + getterName;
        } else {
            getterName = first.toUpperCase() + getterName;
        }

        // add get
        getterName = "get" + getterName;

        // method!
        Method returnMethod = null;

        // attempt to get method
        returnMethod = ValidationMetadataFactory.getMethodByName(inputClass, getterName);

        // if returned parameter size isn't 0, then reset to null
        if (returnMethod != null && returnMethod.getParameterTypes().length != 0) {
            returnMethod = null;
        }

        // check for boolean "is"
        if (returnMethod == null) {

            getterName = getterName.replaceFirst("get", "is");

            // attempt to get method
            returnMethod = ValidationMetadataFactory.getMethodByName(inputClass, getterName);

            // if returned parameter size isn't 0, then reset to null
            if (returnMethod != null && returnMethod.getParameterTypes().length != 0) {
                returnMethod = null;
            }

        }

        // return found getter
        return returnMethod;
    }

    /**
     * Attempts to find the Field for a given Method
     * 
     * @param inputClass
     * @param inputMethod
     * @return
     */
    private static Field extrapolateFieldForGetter(final Class<?> inputClass, final Method inputMethod) {

        // get field name
        String name = "";
        if (inputMethod.getName().indexOf("get") == 0) {
            name = inputMethod.getName().replaceFirst("get", "");
        } else if (inputMethod.getName().indexOf("is") == 0) {
            name = inputMethod.getName().replaceFirst("is", "");
        }

        // return null if the name is borked
        if (name.length() == 0 || name.trim().length() == 0)
            return null;

        // chop off first char
        String fieldName = name.substring(1);

        // get first char
        final String first = name.charAt(0) + "";

        // first is uppercase
        if (first.equals(first.toUpperCase())) {
            fieldName = first.toLowerCase() + fieldName;
        } else {
            fieldName = first.toUpperCase() + fieldName;
        }

        // field holder
        final Field returnField = ValidationMetadataFactory.getFieldByName(inputClass, fieldName);

        // return field
        return returnField;
    }

    /**
     * Gets all of the fields (both declared and inherited) for the given class
     * 
     * @param inputClass
     * @return
     */
    private static HashSet<Field> getAllFields(final Class<?> inputClass) {

        // condition to quit recursive search
        if (inputClass == null || inputClass.equals(Object.class))
            return new HashSet<Field>();

        // fields
        final Field[] openField = inputClass.getDeclaredFields();

        // hashset
        final HashSet<Field> fieldSet = new HashSet<Field>();

        for (final Field f : openField) {
            fieldSet.add(f);
        }

        return fieldSet;
    }

    /**
     * Gets all methods (declared and inherited) for the given class
     * 
     * @param inputClass
     * @return
     */
    private static HashSet<Method> getAllMethods(final Class<?> inputClass) {

        // condition to quit recursive search
        if (inputClass == null || inputClass.equals(Object.class))
            return new HashSet<Method>();

        // fields
        final Method[] openMethod = inputClass.getDeclaredMethods();

        // hashset
        final HashSet<Method> methodSet = new HashSet<Method>();

        for (final Method m : openMethod) {
            methodSet.add(m);
        }


        // add superclass to method set
        methodSet.addAll(ValidationMetadataFactory.getAllMethods(inputClass.getSuperclass()));

        return methodSet;
    }

    /**
     * Searches all methods (inherited and declared) for the method name,
     * returns the first one it finds
     * 
     * @param inputClass
     * @param name
     * @return
     */
    private static Method getMethodByName(final Class<?> inputClass, final String name) {

        final HashSet<Method> methodList = ValidationMetadataFactory.getAllMethods(inputClass);

        Method returnMethod = null;

        for (final Method m : methodList) {
            if (m.getName().equals(name)) {
                returnMethod = m;
                break;
            }
        }

        return returnMethod;

    }

    /**
     * Searches all fields (inherited and declared) for the field name, returns
     * the first one it finds
     * 
     * @param inputClass
     * @param name
     * @return
     */
    private static Field getFieldByName(final Class<?> inputClass, final String name) {

        final HashSet<Field> fieldList = ValidationMetadataFactory.getAllFields(inputClass);

        Field returnField = null;

        for (final Field f : fieldList) {
            if (f.getName().equals(name)) {
                returnField = f;
                break;
            }
        }

        return returnField;

    }

    /**
     * Returns a <code>java.util.Hashset</code> that contains the String key of
     * the sequence name along with a list of all of the groups in that sequence
     * in the proper order.
     * 
     * @param inputClass
     *            the class to check for GroupSequences
     * @return
     */
    public static HashMap<String, ArrayList<String>> getGroupSequenceMap(final Class<?> inputClass) {

        if (inputClass == null || Object.class.equals(inputClass))
            return new HashMap<String, ArrayList<String>>();

        // create empty HashMap
        final HashMap<String, ArrayList<String>> orderingMap = new HashMap<String, ArrayList<String>>();

        // create annotations array list
        final ArrayList<GroupSequence> gsList = new ArrayList<GroupSequence>();

        // by unifying all groupsequences groups into a single array list
        // with the groupsequence annoation we can process them all from
        // the arraylist by using the same logic
        final GroupSequences groupSequences = inputClass.getAnnotation(GroupSequences.class);
        if (groupSequences != null) {

            for (final GroupSequence gs : groupSequences.value()) {
                gsList.add(gs);
            }

        }

        // get groupsequence annotation
        final GroupSequence groupSequence = inputClass.getAnnotation(GroupSequence.class);
        if (groupSequence != null) {
            gsList.add(groupSequence);
        }

        // process list
        for (final GroupSequence gs : gsList) {

            if (gs.sequence() != null) {

                // create new array list for storing group names from the
                // sequence
                final ArrayList<String> groupNames = new ArrayList<String>();

                // add to group names
                for (final String name : gs.sequence()) {
                    groupNames.add(name);
                }

                // if no group names found add "default". no idea /why/ someone
                // would do
                // this but we need to keep this consistant across the
                // application
                if (groupNames.size() == 0)
                    groupNames.add("default");

                // add to map
                if (gs.name() != null && !(gs.name().trim().length() == 0)) {
                    // get gs name
                    final String groupSequenceName = gs.name().trim();

                    // ensure does not already exist in group ordering
                    if (!orderingMap.containsKey(groupSequenceName)) {
                        orderingMap.put(groupSequenceName, groupNames);
                    }
                }
            }

        }

        // process superclass and interfaces
        final HashMap<String, ArrayList<String>> superGroups = ValidationMetadataFactory.getGroupSequenceMap(inputClass.getSuperclass());
        for (final Class<?> interfaceClass : inputClass.getInterfaces()) {
            superGroups.putAll(ValidationMetadataFactory.getGroupSequenceMap(interfaceClass));
        }

        // merge without duplicates so that the rule that only one groupsequence
        // with a name can be defined
        for (final String superGroupName : superGroups.keySet()) {

            // only add if key is not in ordering map
            if (!orderingMap.containsKey(superGroupName) && superGroups.get(superGroupName) != null) {
                orderingMap.put(superGroupName, superGroups.get(superGroupName));
            }
        }

        // return ordering map
        return orderingMap;
    }

    /**
     * Get all super types of specified class. Returns super classes and
     * interfaces as well.
     * 
     * @param class
     * @return set of super types.
     */
    public static Set<Class<?>> getSuperTypes(final Class<?> clazz) {
        //create empty result set
        final HashSet<Class<?>> result = new HashSet<Class<?>>();
        
        //get super class
        final Class<?> superclass = clazz.getSuperclass();
        
        //if super class exists 
        if (superclass != null) {
            //add super class to result set
            result.add(superclass);
            //add all super types of superclass to result set
            result.addAll(getSuperTypes(superclass));
        }
       
        //add all related interfaces to result set
        result.addAll(getRelatedInterfaces(clazz));

        return result;
    }
    
    /** Get all related interfaces.
     * @param class
     * @return set of classes.
     */
    public static Set<Class<?>> getRelatedInterfaces(final Class<?> clazz){
        //create empty result set
        final HashSet<Class<?>> result = new HashSet<Class<?>>();
        
        //get implemented interfaces
        final Class[] interfaces = clazz.getInterfaces();
        
        //for each interface get his inherited interfaces
        for (final Class interfaceClass : interfaces) {
            //add each interface to result set 
            result.add(interfaceClass);
            //add all super interfaces of each interface 
            result.addAll(getRelatedInterfaces(interfaceClass));
        }
        
        return result;
    }
    
    /** Get class level validators for all classes in hierarchy. 
     * @param inputClass
     * @return set of validation packages.
     */
    public static ArrayList<ValidationPackage> getClassLevelValidatorsForClassHierarchy(final Class<?> inputClass) {
        //create empty result set
        final ArrayList<ValidationPackage> result = new ArrayList<ValidationPackage>();
        
        //create related class set
        final Set<Class<?>> validationClassSet = new HashSet<Class<?>>();
        validationClassSet.add(inputClass);
        validationClassSet.addAll(getSuperTypes(inputClass));
        
        //for each class get class level validators
        for (final Class<?> clazz : validationClassSet) {
            result.addAll(getClassLevelValidatorsForClass(clazz));
        }
        
        return result;
    }
    
    /** Get fields validation packages for all classes in hierarchy. 
     * @param inputClass
     * @return set of validation packages.
     */
    public static ArrayList<ValidationPackage> getValidatorsForClassHierarchy( final Class<?> inputClass) {
        //create empty result set
        final ArrayList<ValidationPackage> result = new ArrayList<ValidationPackage>();

        //create related class set
        final Set<Class<?>> validationClassSet = new HashSet<Class<?>>();
        validationClassSet.add(inputClass);
        validationClassSet.addAll(getSuperTypes(inputClass));
        
        //for each class get validators
        for (final Class<?> clazz : validationClassSet) {
            result.addAll(getValidatorsForClass(clazz));
        }
        
        
        return result;
    }
    
    /** Get valid annoted fields validation packages for all classes in hierarchy. 
     * @param inputClass
     * @return set of validation packages.
     */
    public static ArrayList<ValidationPackage> getValidAnnotedPackagesHierarchy( final Class<?> inputClass) {
        //create empty result set
        final ArrayList<ValidationPackage> result = new ArrayList<ValidationPackage>();

        //create related class set
        final Set<Class<?>> validationClassSet = new HashSet<Class<?>>();
        validationClassSet.add(inputClass);
        validationClassSet.addAll(getSuperTypes(inputClass));
        
        //for each class get validation
        for (final Class<?> clazz : validationClassSet) {
            result.addAll(getValidAnnotedPackages(clazz));
        }
        
        
        return result;
    }

}
