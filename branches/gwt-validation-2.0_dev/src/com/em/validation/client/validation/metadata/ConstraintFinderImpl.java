package com.em.validation.client.validation.metadata;

import java.lang.annotation.ElementType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.Scope;

public class ConstraintFinderImpl implements ConstraintFinder {

	private ElementDescriptor backingDescriptor;
	
	private boolean searchChanged = true; 
	
	private Set<ElementType> declaredOnTypes = new HashSet<ElementType>();
	private Set<Scope> onScope = new HashSet<Scope>();
	private Set<Class<?>> matchingGroups = new HashSet<Class<?>>();
	
	/**
	 * Set for caching the results of a search.  By default, the set is empty.
	 * 
	 */
	private Set<ConstraintDescriptor<?>> cachedResults = new HashSet<ConstraintDescriptor<?>>();
	
	protected ConstraintFinderImpl(ElementDescriptor descriptor) {
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
			Set<ConstraintDescriptor<?>> startSet = this.backingDescriptor.getConstraintDescriptors();
			Set<ConstraintDescriptor<?>> resultSet = new HashSet<ConstraintDescriptor<?>>();
			
			for(ConstraintDescriptor<?> descriptor : startSet) {
				//should the result be kept
				boolean keep = true;
				
				//get the groups, as a set
				Set<Class<?>> groups = new HashSet<Class<?>>(Arrays.asList((Class<?>[])descriptor.getAttributes().get("groups")));				
				keep = keep && groups.containsAll(this.matchingGroups);
				
				//todo: get the element type the constraint was declared on
				 
				
				//todo: get the scope of the annotation element
				
				
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
		this.onScope.add(scope);
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
