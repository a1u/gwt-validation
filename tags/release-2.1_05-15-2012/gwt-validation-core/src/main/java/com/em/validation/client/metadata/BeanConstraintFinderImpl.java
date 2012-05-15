package com.em.validation.client.metadata;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.metadata.Scope;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.reflector.IReflector;

/**
 * The constraint finder implementation that uses the special properties of the implemented descriptors to do things
 * like navigate the type hierarchy for local and other scopes.
 * 
 * @author chris
 *
 */
public class BeanConstraintFinderImpl extends AbstractConstraintFinder {

	protected BeanConstraintFinderImpl(IReflector reflector, ElementDescriptor descriptor) {
		this.backingReflector = reflector;
	}

	@Override
	public Set<ConstraintDescriptor<?>> findConstraints(Scope scope, Set<ElementType> declaredOnTypes, Set<Class<?>> matchingGroups) {
		Set<ConstraintDescriptor<?>> initial = new HashSet<ConstraintDescriptor<?>>();
		if(this.backingReflector == null) return initial;
		
		Set<ConstraintDescriptor<?>> results = new HashSet<ConstraintDescriptor<?>>();

		Set<String> propertySet = new HashSet<String>();
		if(Scope.HIERARCHY.equals(scope)) {
			propertySet.addAll(this.backingReflector.getPropertyNames());
		} else if(Scope.LOCAL_ELEMENT.equals(scope)) {
			propertySet.addAll(this.backingReflector.getDeclaredPropertyNames());
		}
		
		//find class level constraint descriptors
		if(declaredOnTypes == null || declaredOnTypes.isEmpty() || declaredOnTypes.contains(ElementType.TYPE)) {
			Set<ConstraintDescriptor<?>> classDescriptors = this.backingReflector.getClassConstraintDescriptors(scope);
			
			for(ConstraintDescriptor<?> descriptor : classDescriptors) {
				//get group set so we can manipulate
				Set<Class<?>> groupSet = descriptor.getGroups();

				boolean keep = true;
				
				//if there are no matching groups, the matching set is the default group
				if(matchingGroups.isEmpty()) {
					matchingGroups.add(Default.class);
				}
				
				//account for the default group if no groups are present
				if(groupSet.isEmpty()) {
					groupSet.add(Default.class);
				}
			
				//check each found group
				boolean found = false;
				Iterator<Class<?>> matchingGroupsIter = matchingGroups.iterator();
				while (!found && matchingGroupsIter.hasNext()) {
					found = this.foundIn(groupSet, matchingGroupsIter.next());
				}
				keep = keep && found;
				
				if(keep) {
					results.add(descriptor);
				}
			}
		}
		
		//get constraints for each property name
		for(String propertyName : propertySet) {
			PropertyDescriptor property = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, propertyName);
			
			//create finder
			ConstraintFinder finder = property.findConstraints()
				.lookingAt(scope)
				.declaredOn(declaredOnTypes.toArray(new ElementType[]{}))
				.unorderedAndMatchingGroups(matchingGroups.toArray(new Class<?>[]{}));
			
			//find and add to results
			results.addAll(finder.getConstraintDescriptors());
		}
		return results;
	}
	
}
