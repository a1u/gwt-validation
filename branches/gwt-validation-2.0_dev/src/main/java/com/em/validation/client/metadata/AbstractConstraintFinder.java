package com.em.validation.client.metadata;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.Scope;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

/**
 * Base class for implementing different types of constraint finders.
 * 
 * @author chris
 *
 */
public abstract class AbstractConstraintFinder implements ConstraintFinder {

	
	/**
	 * Set for caching the results of a search.  By default, the set is empty.
	 */
	protected Set<ConstraintDescriptor<?>> cachedResults = new HashSet<ConstraintDescriptor<?>>();
	
	/**
	 * Used for scope searches
	 * 
	 */
	protected IReflector<?> backingReflector = null;

	/**
	 * When the state of the search values has changed, mark the cache as dirty so that the search will be re-run. 
	 */
	private boolean searchChanged = true; 

	/**
	 * The scope of the search
	 * 
	 * @see Scope
	 */
	private Scope scope = Scope.HIERARCHY;
	
	/**
	 * List of types that the annotation can be declared on
	 */
	private Set<ElementType> declaredOnTypes = new HashSet<ElementType>();
	
	/**
	 * List of groups that are being queried on
	 */
	private Set<Class<?>> matchingGroups = new HashSet<Class<?>>();
	
	public abstract Set<ConstraintDescriptor<?>> findConstraints(Scope scope);
	
	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		//if the state of the class has changed, re-search the constraints
		if(this.searchChanged) {
			Set<ConstraintDescriptor<?>> start = this.findConstraints(this.scope);
			Set<ConstraintDescriptor<?>> results = new LinkedHashSet<ConstraintDescriptor<?>>();
			
			for(ConstraintDescriptor<?> descriptor : start) {
				
				//keep the descriptor in the set
				boolean keep = true; 
				
				//if the size of declared on types is > 0, use it to filter
				if(this.declaredOnTypes.size() > 0) {
					//not yet implemented					
				}

				//skip this test if the decision has already been made not to keep the constraint descriptor
				if(keep) {
					//if there are matching groups at all, perform this test
					if(this.matchingGroups.size() > 0) {
						//get group set so we can manipulate
						Set<Class<?>> groupSet = descriptor.getGroups();
						
						//account for the default group if no groups are present
						if(groupSet.size() == 0) {
							groupSet.add(Default.class);
						}
						
						boolean foundAll = true;
						
						//for all groups in the group set
						for(Class<?> uGroup : this.matchingGroups) {
							
							boolean found = false;
							
							for(Class<?> dGroup : groupSet) {
								
								//check to see if the group the user passed in is a superclass of the
								//group found in the group set on the descriptor
								IReflector<?> rSuper = ReflectorFactory.INSTANCE.getReflector(dGroup);
								
								//this group has not been found
								found = this.foundIn(uGroup, rSuper);
								
								if(found) break;

							}							

							foundAll = foundAll && found;
						}
						
						//the results of this test are combined with the results of the last test for a result
						keep = keep && foundAll;
					}
				}
				
				if(keep) {
					results.add(descriptor);
				}
			}
			
			this.cachedResults = results;
			this.searchChanged = false;
		}		
		return this.cachedResults;
	}

	@Override
	public boolean hasConstraints() {
		return this.getConstraintDescriptors().size() > 0;
	}

	@Override
	public ConstraintFinder lookingAt(Scope scope) {
		this.scope = scope;
		//mark the cache as dirty
		this.searchChanged = true;
		return this;
	}
	@Override
	public ConstraintFinder unorderedAndMatchingGroups(Class<?>... groups) {
		this.matchingGroups.addAll(Arrays.asList(groups));
		//mark the cache as dirty
		this.searchChanged = true;
		return this;
	}
	
	@Override
	public ConstraintFinder declaredOn(ElementType... types) {
		this.declaredOnTypes.addAll(Arrays.asList(types));
		//mark the cache as dirty
		this.searchChanged = true;
		return this;
	}
	
	private boolean foundIn(Class<?> matchingGroup, IReflector<?> lookingReflector) {
		boolean result = false; 
		
		if(lookingReflector == null || lookingReflector.getTargetClass() == null) return false;
		
		if(matchingGroup.equals(lookingReflector.getTargetClass())) {
			result = true;
		}

		//check parent
		if(!result) {
			result = this.foundIn(matchingGroup, lookingReflector.getParentReflector());
		}
		
		//check interfaces
		if(!result) {
			for(IReflector<?> iface : lookingReflector.getInterfaceReflectors()) {
				result = this.foundIn(matchingGroup, iface);
				if(result) {
					break;
				}
			}
		}
		
		return result;
	}
	
}
