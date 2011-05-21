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


import java.util.Set;

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
	
	protected PropertyConstraintFinderImpl(IReflector<?> reflector, String propertyName) {
		this.backingReflector = reflector;
		this.propertyName = propertyName;
	}

	@Override
	public Set<ConstraintDescriptor<?>> findConstraints(Scope scope) {
		return this.backingReflector.getConstraintDescriptors(this.propertyName,scope);
	}
	
}
