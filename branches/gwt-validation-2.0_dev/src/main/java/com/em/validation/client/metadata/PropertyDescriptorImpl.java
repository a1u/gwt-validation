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
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.reflector.IReflector;

public class PropertyDescriptorImpl extends ProtoDescriptor implements PropertyDescriptor {

	protected String propertyName = "";
	
	public PropertyDescriptorImpl(IReflector<?> reflector, String name) {
		super(reflector);
		this.propertyName = name;
	}

	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		return this.backingReflector.getConstraintDescriptors(this.propertyName);
	}

	@Override
	public Class<?> getElementClass() {
		return this.backingReflector.getPropertyType(this.propertyName);
	}

	@Override
	public boolean hasConstraints() {
		return this.getConstraintDescriptors().size() > 0;
	}

	@Override
	public String getPropertyName() {
		return this.propertyName;
	}

	@Override
	public boolean isCascaded() {
		return this.backingReflector.isCascaded(this.propertyName);
	}

}
