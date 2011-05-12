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


import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

public interface IReflector<T> {

	/**
	 * Get the value of the field "name" found in the target object "target".
	 * 
	 * @param target
	 * @param name
	 * @return
	 */
	public Object getValue(String name, T target);

	/**
	 * Get the bean accessible name (short name) of all of the publicly accessible methods contained in the given concrete class.
	 * 
	 * @return
	 */
	public Set<String> getPropertyNames();
		
	/**
	 * Returns all of the constraint descriptors declared on a given field
	 * 
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors();
	
	/**
	 * Given then name of the field or method, return a list of constraint descriptors for that annotation.
	 * 
	 * @param name
	 * @return
	 */
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors(String name);
	
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
}
