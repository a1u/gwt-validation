package com.google.gwt.validation.server;

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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.validation.client.AbstractValidator;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IConstraint;
import com.google.gwt.validation.rebind.ValidationMetadataFactory;
import com.google.gwt.validation.rebind.ValidationPackage;

/**
 * This class serves as a server-side implementation of IValidator that can 
 * be used on the backend without the overhead / fuss of the GWT.create() 
 * mechanism.
 * <br/><br/>
 * This class is designed to parallel as closely as possible the code for the
 * classes that are created by the generators for client side validation.  This
 * is so that the same issues / bugs / enhancements can be fixed and validated
 * here before tyring to write code generation code that produces the same effect.
 * 
 * @author chris
 *
 * @param <T> the class to validate
 */
public class ServerValidator<T> extends AbstractValidator<T> {

	/**
	 * Get a list of group sequence mappings given the input class
	 * 
	 * @param inputClass
	 * @return
	 */
	@Override
    protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(final Class<?> inputClass) {
		return ValidationMetadataFactory.getGroupSequenceMap(inputClass);
	}

	@Override
    public Set<InvalidConstraint<T>> performValidation(final T object, String propertyName, final ArrayList<String> groups, final HashSet<String> processedGroups, final HashSet<String> processedObjects) {
		//hash set for results
		final HashSet<InvalidConstraint<T>> icSet = new HashSet<InvalidConstraint<T>>();
		
		//on null it would fail anyway, return empty icSet
		if(object == null) return icSet;
		
		//groups listing
		String grouplisting = "";
		for(final String group : groups) {
			grouplisting += ":" + group;
		}
		
		//get input class
		final Class<?> inputClass = object.getClass();

		//condition property name
		if(propertyName != null && propertyName.trim().length() == 0) {
			propertyName = null;
		}
		
		//validation package list
		final ArrayList<ValidationPackage> vpList = ValidationMetadataFactory.getValidatorsForClassHierarchy(inputClass);
		
		for(final ValidationPackage vp : vpList) {
			
			//get implementing constraint
			final IConstraint<Annotation> constraint = vp.getImplementingConstraint();
			
			try {
				//only check if group is proper and the propertyname is selected
				if((propertyName == null || vp.getItemName().equals(propertyName)) && (groups.size() == 0 || this.intersects(groups, vp.getGroups())) && (processedGroups.size() == 0 || !this.intersects(processedGroups, vp.getGroups()))) {
					//get value
					final Object value = vp.getMethod().invoke(object, new Object[] {});
					
					//if that constraint is not valid, then die
					if(!constraint.isValid(value)) {
						//build ic
						final InvalidConstraint<T> ic = vp.buildInvalidConstraint(object);
						//add value
						ic.setValue(value);
						//add ic to list
						icSet.add(ic);						
					} 
				}  
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		
		//only perform class level validation if the a valueValidation is 
		//not being done
		if(object != null) {
		
			//get class level validation list
			final ArrayList<ValidationPackage> vpClassList = ValidationMetadataFactory.getClassLevelValidatorsForClassHierarchy(inputClass);
			
			//process class annotations
			for(final ValidationPackage vp : vpClassList) {
				
				//get implementing constraint
				final IConstraint<Annotation> constraint = vp.getImplementingConstraint();
				
				//check against groups and propertyname
				if((propertyName == null || vp.getItemName().equals(propertyName)) && (groups.size() == 0 || this.intersects(groups, vp.getGroups())) && (processedGroups.size() == 0 || !this.intersects(processedGroups, vp.getGroups()))) {
					
					//do validity
					if(!constraint.isValid(object)) {
						//build ic
						final InvalidConstraint<T> ic = vp.buildInvalidConstraint(object);
						//set invalid value as self
						ic.setValue(object);
						//add to list
						icSet.add(ic);
					} 		
	
				} 
			}
			
		}
		
		//get @Valid package list
		final ArrayList<ValidationPackage> vpValidList = ValidationMetadataFactory.getValidAnnotedPackagesHierarchy(inputClass);
		
		//process annotations
		for(final ValidationPackage vp : vpValidList) {
			
			//no reason to get implementing constraint... it is null.
			
			//make sure that the field name is right
			if(propertyName == null || vp.getItemName().equals(propertyName)) {
			
				/*
				 * Try not to be confused by the following code, I fully understand
				 * that I can use instanceof to get the result that I want but
				 * I've tried to keep this class as similar to the code creator
				 * for the client side validator as I can.  In this way I'm
				 * testing the creator before I even write it by ensuring that 
				 * the logic behind the writing is at lest correct.  It is for this
				 * reason that in the code below i've tried to ignore the fact
				 * that I have the actual object instance.  -- Chris Ruffalo 
				 * 
				 */
				
				//get object that will be validated
				try {
					//get object
					final Object objectFromValid = vp.getMethod().invoke(object, new Object[]{});
						
					//get return thingy
					final Class<?> returnType = vp.getMethod().getReturnType();
					
					//local property
					final String localProperty = vp.getItemName();
					
					//create validator
					final ServerValidator<Object> validator = new ServerValidator<Object>();
					
					//can't really do much with a null object and no reason to validate it, this
					//case is usually covered by the @NotNull constraint
					if(objectFromValid != null) {
						
						//this is used to prevent re-processing and such that takes up time
						//through casting and exception and also uses reflection which isn't
						//the most optimal of mechanisms for doing this sort of thing.
						boolean validated = false;
						
						try {
							if(returnType.asSubclass(Object[].class) != null) {
								
								//get type name of the type to validate
								final Type typeToValidate = returnType.getComponentType();
								String typeName = typeToValidate.toString();
								typeName = typeName.substring(typeName.indexOf(" "));
								
								//get object array
								final Object[] oArray = (Object[])objectFromValid;
							
								//for each object in the array
								for(final Object o : oArray) {
									
									//object identifier
									final String objIdent = o.hashCode() + ":" + grouplisting.hashCode();
									
									//only process non-processed objects
									if(!processedObjects.contains(objIdent)) {
										//add to processed objects
										processedObjects.add(objIdent);
	
										//validate children
										icSet.addAll(this.unrollConstraintSet(object, localProperty, validator.performValidation(o, null, groups, processedGroups, processedObjects)));
									}
									
								}

								//has been validated somehow, no other method needed
								validated = true;
							} 
						} catch (final ClassCastException ccex) {
							
						}
						
						if(!validated) {
						
							try {
								final Class<?> collectionClass = returnType.asSubclass(Collection.class);
								
								if(collectionClass != null) {
	
									final Type typeToValidate = ((ParameterizedType)vp.getMethod().getGenericReturnType()).getActualTypeArguments()[0];
									String typeName = typeToValidate.toString();
									typeName = typeName.substring(typeName.indexOf(" "));
								
									//get object collection
									//suppress warnings because at this time we can be reasonably sure that it is of
									//type Collection and that it does extend object.  I hate to rely on the "magic"
									//of trusting the compiler but for some of this stuff it has become imperative.
									@SuppressWarnings("unchecked")
                                    final
									Collection<Object> oCollection = (Collection<Object>)objectFromValid;
									
									//for each object in the collection
									for(final Object o : oCollection) {
										
										//object identifier
										final String objIdent = o.hashCode() + ":" + grouplisting.hashCode();
										
										//only process non-processed objects
										if(!processedObjects.contains(objIdent)) {
											//add to processed objects
											processedObjects.add(objIdent);
											//validate children
											icSet.addAll(this.unrollConstraintSet(object, localProperty, validator.performValidation(o, null, groups, processedGroups, processedObjects)));
										}
									}
									
									//has been validated somehow, no other method needed
									validated = true;
								} 
							} catch (final ClassCastException ccex) {
								
							}
						}
						
						if(!validated) {
							try {
								
								final Class<?> mapClass = returnType.asSubclass(Map.class);
								
								if(mapClass != null) {
									
									//get type name
									final Type typeToValidate = ((ParameterizedType)vp.getMethod().getGenericReturnType()).getActualTypeArguments()[1];
									String typeName = typeToValidate.toString();
									typeName = typeName.substring(typeName.indexOf(" "));
	
									//get object list
									//suppress warnings because at this time we can be reasonably sure that it is of
									//type Map and that it does extend object.  I hate to rely on the "magic"
									//of trusting the compiler but for some of this stuff it has become imperative.
									@SuppressWarnings("unchecked")
                                    final
									Map<Object,Object> oMap = (Map<Object,Object>)objectFromValid;
									
									//for each object in the map value set
									for(final Object o : oMap.values()) {
										
										//object identifier
										final String objIdent = o.hashCode() + ":" + grouplisting.hashCode();
										
										//only process non-processed objects
										if(!processedObjects.contains(objIdent)) {
											//add to processed objects
											processedObjects.add(objIdent);

											//validate children
											icSet.addAll(this.unrollConstraintSet(object, localProperty, validator.performValidation(o, null, groups, processedGroups, processedObjects)));
										}
									}

									//has been validated somehow, no other method needed
									validated = true;
								}
							} catch (final ClassCastException ccex) {

							}
							
						}

						//if some other method has not been used...
						if(!validated) {

							//object identifier
							final String objIdent = objectFromValid.hashCode() + ":" + grouplisting.hashCode();
							
							//only process if it hasn't been processed already
							if(!processedObjects.contains(objIdent)) {
								//add object to processed objects
								processedObjects.add(objIdent);
								
								//validate as single object
								icSet.addAll(this.unrollConstraintSet(object, propertyName, validator.performValidation(objectFromValid, null, groups, processedGroups, processedObjects)));
							} 
						} 						
					}
					
				} catch (final IllegalArgumentException e) {
					e.printStackTrace();
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				} catch (final InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return icSet;
	}
		
}
