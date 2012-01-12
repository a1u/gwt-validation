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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.Scope;

import com.em.validation.client.reflector.IReflector;

/**
 * The constraint finder implementation that uses the special properties of the implemented descriptors to do things
 * like navigate the type hierarchy for local and other scopes.
 *
 * @author chris
 *
 */
public class PropertyConstraintFinderImpl extends AbstractConstraintFinder {

	private String propertyName = "";

	protected PropertyConstraintFinderImpl(IReflector reflector, String propertyName) {
		this.backingReflector = reflector;
		this.propertyName = propertyName;
	}

	@Override
	public Set<ConstraintDescriptor<?>> findConstraints(Scope scope, Set<ElementType> declaredOnTypes, Set<Class<?>> matchingGroups) {
		Set<ConstraintDescriptor<?>> start = this.backingReflector.getConstraintDescriptors(this.propertyName,scope);
		Set<ConstraintDescriptor<?>> results = new LinkedHashSet<ConstraintDescriptor<?>>();

		for(ConstraintDescriptor<?> descriptor : start) {

			//keep the descriptor in the set
			boolean keep = true;

			//skip this test if the decision has already been made not to keep the constraint descriptor
			if(keep) {
				//if the size of declared on types is > 0, use it to filter
				if(declaredOnTypes.size() > 0) {
					//get declared on set from the backing reflector 
					Set<ElementType> propertyFoundOn = this.backingReflector.declaredOn(scope, this.propertyName, descriptor);

					keep = false;

					for(ElementType type : propertyFoundOn) {
						if(declaredOnTypes.contains(type)) {
							keep = true;
							break;
						}
					}

				}

				//if there are no matching groups, the matching set is the default group
				if(matchingGroups.isEmpty()) {
					matchingGroups.add(Default.class);
				}

				//finding the groups
				//any of the groups in the matching group set
				//MUST either:
				//be found in the descriptor's group set
				//be a superclass of a group in the descriptors group set

				//get group set so we can manipulate
				Set<Class<?>> groupSet = descriptor.getGroups();

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
			}

			if(keep) {
				results.add(descriptor);
			}
		}

		return results;
	}

}
