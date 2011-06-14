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


import java.util.HashSet;
import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.reflector.IReflector;

/**
 * The implementation of the bean descriptor class
 * 
 * @author chris
 *
 */
public class BeanDescriptorImpl extends ProtoDescriptor implements BeanDescriptor,ElementDescriptor {

	public BeanDescriptorImpl(IReflector<?> reflector) {
		super(reflector);
	}

	@Override
	public Set<PropertyDescriptor> getConstrainedProperties() {
		Set<PropertyDescriptor> propertyDescriptors = new HashSet<PropertyDescriptor>();
		for(String propertyName : this.backingReflector.getPropertyNames()) {
			PropertyDescriptor descriptor = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, propertyName);
			if(descriptor.hasConstraints()) {
				propertyDescriptors.add(descriptor);
			}
		}		
		return propertyDescriptors;
	}

	@Override
	public PropertyDescriptor getConstraintsForProperty(String name) {
		if(!this.backingReflector.getPropertyNames().contains(name)) {
			return null;
		}
		PropertyDescriptor descriptor = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, name);
		if(!descriptor.hasConstraints()) {
			return null;
		}
		return descriptor;
	}

	@Override
	public boolean isBeanConstrained() {
		return this.backingReflector.getConstraintDescriptors().size() > 0;
	}
	
	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		return backingReflector.getClassConstraintDescriptors();
	}

	@Override
	public Class<?> getElementClass() {
		return this.backingReflector.getTargetClass();
	}

	@Override
	public boolean hasConstraints() {
		return !this.getConstraintDescriptors().isEmpty();
	}

	@Override
	public ConstraintFinder findConstraints() {
		final class PrivateConstraintFinderImpl extends BeanConstraintFinderImpl {
			public PrivateConstraintFinderImpl(IReflector<?> reflector, ElementDescriptor descriptor) {
				super(reflector,descriptor);
			}			
		}
		
		return new PrivateConstraintFinderImpl(this.backingReflector,this);
	}
	
}
