package com.em.validation.client.metadata;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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


import java.lang.annotation.ElementType;
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
				
				//if there are matching groups at all, perform this test
				if(matchingGroups.size() > 0) {
					//finding the groups
					//all of the groups in the matching group set
					//MUST either:
					//be found in the descriptor's group set
					//be a superclass of a group in the descriptors group set

					//get group set so we can manipulate
					Set<Class<?>> groupSet = descriptor.getGroups();
					
					//account for the default group if no groups are present
					if(groupSet.size() == 0) {
						groupSet.add(Default.class);
					}

					//check each found group
					for(Class<?> mGroup : matchingGroups) {
						//check to see if mGroup or one of the interfaces or superclasses of mGroup is found in the group set on the descriptor
						keep = keep && this.foundIn(groupSet, mGroup);
					}					
				}
			}
			
			if(keep) {
				results.add(descriptor);
			}
		}
		
		return results;
	}

}
