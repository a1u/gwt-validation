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
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.Scope;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.Reflector;

/**
 * The constraint finder implementation that uses the special properties of the implemented descriptors to do things
 * like navigate the type hierarchy for local and other scopes.
 * 
 * @author chris
 *
 */
public class ConstraintFinderImpl implements ConstraintFinder {

	/**
	 * The descriptor (which uses an IReflector instance) that provides the metadata for the search 
	 */
	private ElementDescriptor backingDescriptor;
	
	/**
	 * Used for scope searches
	 * 
	 */
	private IReflector<?> backingReflector = null;
	
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
	
	/**
	 * Set for caching the results of a search.  By default, the set is empty.
	 */
	private Set<ConstraintDescriptor<?>> cachedResults = new HashSet<ConstraintDescriptor<?>>();
	
	protected ConstraintFinderImpl(IReflector<?> reflector, ElementDescriptor descriptor) {
		this.backingReflector = reflector;
		this.backingDescriptor = descriptor;
		this.cachedResults = this.backingDescriptor.getConstraintDescriptors();
	}
	
	@Override
	public ConstraintFinder declaredOn(ElementType... types) {
		this.declaredOnTypes.addAll(Arrays.asList(types));
		//mark the cache as dirty
		this.searchChanged = true;
		return this;
	}

	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		//if the state of the class has changed, re-search the constraints
		if(this.searchChanged) {
			
			Set<ConstraintDescriptor<?>> startSet = null;
			//use the scope to intially constrain the starting set
			if(Scope.HIERARCHY.equals(this.scope)) {
				startSet = this.backingDescriptor.getConstraintDescriptors();
			} else {
				if(this.backingReflector instanceof Reflector) {
					Reflector<?> reflector = (Reflector<?>)this.backingReflector;
					startSet = reflector.getConstraintDescriptors(this.scope);
				}
			}
			
			Set<ConstraintDescriptor<?>> resultSet = new HashSet<ConstraintDescriptor<?>>();
			
			for(ConstraintDescriptor<?> descriptor : startSet) {
				//should the result be kept
				boolean keep = true;
				
				//get the groups, as a set
				Set<Class<?>> groups = new HashSet<Class<?>>(Arrays.asList((Class<?>[])descriptor.getAttributes().get("groups")));				
				keep = keep && groups.containsAll(this.matchingGroups);
				
				//todo: get the element type the constraint was declared on
				 
				
				if(keep) {
					resultSet.add(descriptor);
				}
			}

			//save the result set into the cache and mark the cache as clean
			this.cachedResults = resultSet;
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

}
