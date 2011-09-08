package com.em.validation.client.reflector;

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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.Scope;

public abstract class AbstractCompiledReflector<T> extends Reflector<T> {

	//compile time generated support for marking cascaded properties
	protected Set<String> cascadedProperties = new LinkedHashSet<String>();

	//compile time generated support for "declared on"
	protected Map<String,Set<ConstraintDescriptor<?>>> declaredOnMethod = new HashMap<String,Set<ConstraintDescriptor<?>>>(); 
	protected Map<String,Set<ConstraintDescriptor<?>>> declaredOnField = new HashMap<String,Set<ConstraintDescriptor<?>>>();

	@Override
	public boolean isCascaded(String propertyName) {
		boolean result = false;
		
		//check the cascaded properties set first
		result = this.cascadedProperties.contains(propertyName);
		
		//if still false after checking property and field, continue to check other values
		if(result == false) {
			if(this.superReflector != null) {
				result = this.superReflector.isCascaded(propertyName);
			}
			if(result == false) {
				for(IReflector<?> iface : this.reflectorInterfaces) {
					result = iface.isCascaded(propertyName);
					if(result) break;
				}
			}			
		}
		
		return result;
	}
	
		
	/**
	 * Get the return type of a given property name
	 */
	public Class<?> getPropertyType(String name) {
		Class<?> result = this.propertyTypes.get(name);
		
		if(result == null) {
			if(this.superReflector != null) {
				result = this.superReflector.getPropertyType(name);
			}
			if(result == null) {
				for(IReflector<?> iface : this.reflectorInterfaces) {
					result = iface.getPropertyType(name);
					if(result != null) break;
				}
			}	
		}
		
		return result;
	}
	
	@Override
	public Set<ElementType> declaredOn(Scope scope, String property, ConstraintDescriptor<?> descriptor) {
		Set<ElementType> results = new LinkedHashSet<ElementType>();
		
		if(this.declaredOnField.get(property) != null && this.declaredOnField.get(property).contains(descriptor)) {
			results.add(ElementType.FIELD);
		}
		
		if(this.declaredOnMethod.get(property) != null && this.declaredOnMethod.get(property).contains(descriptor)) {
			results.add(ElementType.METHOD);
		}

		//walk the chain if need be
		if(Scope.HIERARCHY.equals(scope)) {
			if(this.superReflector != null) {
				results.addAll(this.superReflector.declaredOn(scope,property,descriptor));
			}
			for(IReflector<?> iface : this.reflectorInterfaces) {
				if(iface != null) {
					results.addAll(iface.declaredOn(scope,property,descriptor));
				}
			}
		}

		return results;
	}	

	
}
