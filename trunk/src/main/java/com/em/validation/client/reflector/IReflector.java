package com.em.validation.client.reflector;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/


import java.lang.annotation.ElementType;
import java.util.Set;

import javax.validation.GroupSequence;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.Scope;

public interface IReflector {
	
	/**
	 * Get the value of the field "name" found in the target object "target".
	 * 
	 * @param target
	 * @param name
	 * @return
	 */
	public Object getValue(String name, Object target);

	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames();
	
	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods delcared on the bean itself (not on superclasses or interfaces)
	 * 
	 * @return
	 */
	public Set<String> getDeclaredPropertyNames();
		
	/**
	 * Returns all of the constraint descriptors declared on every field and on the class itself
	 * 
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors();
	
	/**
	 * Returns all of the constraint descriptors declared on every field and on the class itself within the defined scope
	 * 
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(Scope scope);
	
	/**
	 * Given then name of the field or method, return a list of constraint descriptors.
	 * 
	 * @param name
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name);
	
	/**
	 * Given then name of the field or method, return a list of constraint descriptors.
	 * 
	 * @param name
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name, Scope scope);
	
	/**
	 * Returns the descriptors from the class level
	 * 
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getClassConstraintDescriptors();
	
	/**
	 * Returns the descriptors from the class level for the given scope
	 * 
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getClassConstraintDescriptors(Scope scope);
	
	/**
	 * Get the class that this reflector is made to operate on
	 * 
	 * @return
	 */
	public Class<?> getTargetClass();
	
	/**
	 * Return the property type for a given name
	 * 
	 * @param name
	 * @return
	 */
	public Class<?> getPropertyType(String name);
	
	/**
	 * Returns true if the underlying property is cascaded.  False otherwise
	 * 
	 * @param propertyName
	 * @return
	 */
	public boolean isCascaded(String propertyName);
	
	/**
	 * Get the parent reflector
	 * 
	 * @return
	 */
	public IReflector getParentReflector();
	
	/**
	 * Get the set of reflector interfaces
	 * 
	 * @return
	 */
	public Set<IReflector> getInterfaceReflectors();
	
	/**
	 * Return the group sequence to use in place of default, if not available return an empty class array.
	 * 
	 * @see GroupSequence
	 * @return
	 */
	public Class<?>[] getGroupSequence();
	
	/**
	 * Returns true if the size of the group sequence array is greater than 0.  This indicates that the validator should process the group sequence
	 * 
	 * @return
	 */
	public boolean hasGroupSequence();
	
	/**
	 * Returns the set of element types that the given constraint descriptor is declared on
	 * 
	 * @param property
	 * @param descriptor
	 * @return
	 */
	public Set<ElementType> declaredOn(Scope scope, String property, ConstraintDescriptor<?> descriptor);
}
