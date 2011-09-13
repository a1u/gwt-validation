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
import java.util.HashSet;
import java.util.Set;

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
