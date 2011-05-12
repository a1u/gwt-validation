package com.em.validation.client.reflector;

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


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.Scope;

public abstract class Reflector<T> implements IReflector<T> {

	/**
	 * Set of unique property names
	 */
	protected Set<String> properties = new HashSet<String>();
	
	/**
	 * Store the property return types
	 */
	protected Map<String,Class<?>> propertyTypes = new HashMap<String, Class<?>>();
	
	protected IReflector<?> superReflector = null;
	protected Set<IReflector<?>> reflectorInterfaces = new HashSet<IReflector<?>>();
	
	/**
	 * The target class of the reflector
	 * 
	 */
	protected Class<?> targetClass = null;
	
	public Class<?> getTargetClass() {
		return this.targetClass;
	}
	
	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames() {
		return this.properties;
	}
	
	public Class<?> getPropertyType(String name) {
		return this.propertyTypes.get(name);
	}
	
	/**
	 * Direct access to the constraint descriptors, from which we can get constraints
	 * 
	 */
	protected Map<String, Set<ConstraintDescriptor<?>>> constraintDescriptors = new HashMap<String, Set<ConstraintDescriptor<?>>>();
	
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(Scope scope) {
		Set<ConstraintDescriptor<?>> outputSet = new HashSet<ConstraintDescriptor<?>>();
		for(Set<ConstraintDescriptor<?>> partialSet : this.constraintDescriptors.values()) {
			outputSet.addAll(partialSet);
		}
		if(Scope.HIERARCHY.equals(scope)) {
			if(this.superReflector != null) {
				outputSet.addAll(this.superReflector.getConstraintDescriptors());
			}
			for(IReflector<?> iface : this.reflectorInterfaces) {
				outputSet.addAll(iface.getConstraintDescriptors());
			}
		}
		return outputSet;
	}
	
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		return this.getConstraintDescriptors(Scope.HIERARCHY);
	}
	
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name, Scope scope) {
		Set<ConstraintDescriptor<?>> descriptors = this.constraintDescriptors.get(name);
		if(descriptors == null) {
			descriptors = new HashSet<ConstraintDescriptor<?>>();
			this.constraintDescriptors.put(name, descriptors);
		}
		if(Scope.HIERARCHY.equals(scope)) {
			if(this.superReflector != null) {
				descriptors.addAll(this.superReflector.getConstraintDescriptors(name));	
			}
			for(IReflector<?> iface : this.reflectorInterfaces) {
				descriptors.addAll(iface.getConstraintDescriptors(name));
			}
		}		
		return this.constraintDescriptors.get(name);
	}

	/**
	 * Get the constraint descriptors present on the given property
	 * 
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name){
		return this.getConstraintDescriptors(name, Scope.HIERARCHY);
	}

	@SuppressWarnings("unchecked")
	public Object getValueFromObject(String name, Object target) {
		return this.getValue(name, (T)target);
	}	
	
	protected Object getSuperValues(String name, T target) {
		//check super classes
		Object value = null;
		if(this.superReflector != null && this.superReflector instanceof Reflector) {
			value = ((Reflector<?>)this.superReflector).getValueFromObject(name, target);
		}		
		//if the value is still null, check interfaces
		if(value == null) {
			for(IReflector<?> iface : this.reflectorInterfaces) {
				if(iface != null && iface instanceof Reflector) {
					value = ((Reflector<?>)iface).getValueFromObject(name, target);
				}
				if(value != null) break;
			}
		}	
		
		return value;
	}	
	
	
	public void setSuperReflector(IReflector<?> superReflector) {
		this.superReflector = superReflector;
	}
	
	public void addReflectorInterface(IReflector<?> reflectorInterface) {
		this.reflectorInterfaces.add(reflectorInterface);
	}
}
