package com.google.gwt.validation.client;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.validation.client.interfaces.IValidator;

/**
 * Wrapper for IValidator that contains utility methods for validation
 * 
 * @author chris
 *
 * @param <IValidatable> the validatable class to validate
 */
public abstract class AbstractValidator<T> implements IValidator<T> {
	
	/**
	 * Gets the group sequence mapping from the implementing class
	 * 
	 * @param inputClass
	 * @return
	 */
	protected abstract HashMap<String, ArrayList<String>> getGroupSequenceMapping(Class<?> inputClass);
	
	/**
	 * Actual validation engine class that performs validation internally
	 * 
	 * @param object
	 * @param propertyName
	 * @param groups
	 * @param processedGroups
	 * @param processedObjects
	 * @return
	 */
	public abstract Set<InvalidConstraint<T>> performValidation(T object, String propertyName, ArrayList<String> groups, HashSet<String> processedGroups, HashSet<String> processedObjects);
	
	public Set<InvalidConstraint<T>> validate(T object, String... groups) {
		//call validate property with null property
		return this.validateProperty(object, null, groups);
		
	}
	
	public Set<InvalidConstraint<T>> validateProperty(T object, String propertyName, String... groups) {
		//copy groups to arraylist for processing
		ArrayList<String> grouplist = new ArrayList<String>();
		for(String group : groups) {
			grouplist.add(group);
		}
		
		//call call validate property with arraylist
		return this.validateProperty(object, propertyName, grouplist);
	}
	
	/**
	 * Validate the property like the interface but accept an array list instead of an array
	 * 
	 * @param object
	 * @param propertyName
	 * @param groups
	 * @return
	 */
	protected Set<InvalidConstraint<T>> validateProperty(T object, String propertyName, ArrayList<String> groups) {
		
		//do inner call to processing method
		return this.prepareValidation(object, propertyName, null, groups);
	}

	/**
	 * Group level validation performed for pre-processing of the engine.
	 * 
	 * @param object
	 * @param propertyName
	 * @param groups
	 * @return
	 */
	protected Set<InvalidConstraint<T>> prepareValidation(T object, String propertyName, Object inputValue, ArrayList<String> groups) {
		//hash set for results
		HashSet<InvalidConstraint<T>> icSet = new HashSet<InvalidConstraint<T>>();
		
		//processed objects hashset
		HashSet<String> processedObjects = new HashSet<String>();
		
		//group sequences		
		HashMap<String, ArrayList<String>> groupSequences = this.getGroupSequenceMapping(object.getClass());
		
		//always add defautl group when size is 0
		if(groups.size() == 0) {
			groups.add("default");
		} 

		//already processed groups
		HashSet<String> processedGroups = new HashSet<String>();
		
		//if group sequences exist
		if(groupSequences != null && groupSequences.size() > 0 && groups.size() > 0) {
			
			//go through the groups by each group name so that
			//we can get the proper ordering for the groups in that 
			for(String groupSelectionName : groups) {
				
				//if constraints have been found, stop
				//this is from the JSR-303 standard to not
				//continue processing groups if some are found
				//in a previous sequence step
				if(icSet.size() > 0) { break; }
				
				//if group selection has not been processed 
				if(!processedGroups.contains(groupSelectionName)) {

					//add selected group to processed groups becaause we want to prevent 
					//@GroupSequence(name="one", sequence={"one", "two"})
					//from doing "strange" things
					processedGroups.add(groupSelectionName);					
					
					//when the arraylist contains the selected group that was passed in
					if(groupSequences.containsKey(groupSelectionName)) {
					
						//group sequence belonging to that key
						ArrayList<String> groupSequenceForSelection = groupSequences.get(groupSelectionName);
						
						//process each group in the sequence
						for(String groupInSequence : groupSequenceForSelection) {
							
							//ensure it has not been processed
							if(!processedGroups.contains(groupInSequence)) {
								
								//create grouplist with one group
								ArrayList<String> temporaryGroupList = new ArrayList<String>();
								temporaryGroupList.add(groupInSequence);								

								//process and add to icSet
								icSet.addAll(this.performValidation(object, propertyName, temporaryGroupList, processedGroups, processedObjects));
								
								//add to processed groups
								processedGroups.add(groupInSequence);								
							}
							
							//break if the icSet is > 0
							if(icSet.size() > 0) { break; }
							
							//done with groups in sequence
						}
					
						//done with making sure that the selected group is in the group sequences
					}				

				
					//done with making sure group has not been processed @ groupsequence level
				}
			
				//done with all groups in selection 
			}		
			
			//done with size > 0 stuff
		} 
		//otherwise just perform normal group-based validation
		else {
			icSet.addAll(this.performValidation(object, propertyName, groups, processedGroups, processedObjects));
		}

		//return complete set
		return icSet;
	}	
	
	/**
	 * Returns true if and only if list A intersects list B at
	 * one or more points. 
	 * 
	 * @param collectionA collection of values A
	 * @param collectionB collection of values B
	 * @return
	 */
	protected boolean intersects(Collection<? extends Object> collectionA, Collection<? extends Object> collectionB) {
		
		//eventual return value
		boolean value = false;
		
		//check
		for(Object o : collectionA) {
			if(collectionB.contains(o)) {
				value = true;
				break;
			}
		}
		
		//return value
		return value;
	}
	

	/**
	 * I'm fairly certain this exists elsewhere but I want to use it in this context
	 * to be GWT safe and not rely on emulation. Also to be null or empty safe.
	 * 
	 * @param inputArray
	 * @return
	 */
	protected ArrayList<String> arrayToList(String... inputArray) {
		
		//create array list
		ArrayList<String> list = new ArrayList<String>();
		
		if(inputArray != null && inputArray.length > 0) {
		
			for(String input : inputArray) {
				list.add(input);
			}
			
		}	
		
		//return list
		return list;
	}
	
	/**
	 * Turns an invalid constraint at a lower level to one at the proper higher level
	 * @param <X>
	 * 
	 * @return
	 */
	protected <X> InvalidConstraint<T> unrollConstraint(T object, String property, InvalidConstraint<X> insideConstraint) {
		//new property path
		String prop = property + "." + insideConstraint.getPropertyPath();
		//prevent things like "blob.this" and just get "blob"
		//also blob.null
		prop = prop.replace(".this", "");
		prop = prop.replace(".null", "");
		
		//new invalid constraint
		InvalidConstraint<T> newIC = new InvalidConstraint<T>(property, insideConstraint.getMessage());
		newIC.setPropertyPath(prop);
		newIC.setInvalidObject(object);
		newIC.setValue(insideConstraint.getValue());
		
		//return unrolled constraint
		return newIC;
	}
	
	/**
	 * Uses unrollConstraint to unroll an entire set and return them as a set of invalid constraints and return them
	 * as a set of the type that corresponds to this validator.
	 * 
	 * @param <X>
	 * @param object
	 * @param property
	 * @param insideConstraintSet
	 * @return
	 */
	protected <X> Set<InvalidConstraint<T>> unrollConstraintSet(T object, String property, Set<InvalidConstraint<X>> insideConstraintSet) {
		//hash set for results
		HashSet<InvalidConstraint<T>> icSet = new HashSet<InvalidConstraint<T>>();

		//unroll set
		for(InvalidConstraint<?> ic : insideConstraintSet) {
			icSet.add(unrollConstraint(object, property, ic));
		}
		
		//return result set
		return icSet;
	}
	
}
