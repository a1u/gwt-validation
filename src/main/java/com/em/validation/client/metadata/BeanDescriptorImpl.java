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
public class BeanDescriptorImpl extends ProtoDescriptor implements BeanDescriptor {

	public BeanDescriptorImpl(IReflector reflector) {
		super(reflector);
	}

	@Override
	public Set<PropertyDescriptor> getConstrainedProperties() {
		Set<PropertyDescriptor> propertyDescriptors = new HashSet<PropertyDescriptor>();
		
		//this was added as part of the work-around for the problems found in issue #32.  this just gets us passed
		//one set of exceptions and not passed the entire problem.  there was a null pointer exception being thrown
		//by one of the generated classes at this point because it's backing reflector hadn't been generated.
		if(this.backingReflector == null || this.backingReflector.getPropertyNames() == null) {
			return propertyDescriptors;
		}
		
		for(String propertyName : this.backingReflector.getPropertyNames()) {
			PropertyDescriptor descriptor = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, propertyName);
			if(descriptor.hasConstraints() || descriptor.isCascaded()) {
				propertyDescriptors.add(descriptor);
			}
		}		
		return propertyDescriptors;
	}

	@Override
	public PropertyDescriptor getConstraintsForProperty(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Property name cannot be null.");
		}
		
		if(!this.backingReflector.getPropertyNames().contains(name)) {
			return null;
		}
		PropertyDescriptor descriptor = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, name);
		if(!descriptor.hasConstraints() && !descriptor.isCascaded()) {
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
			public PrivateConstraintFinderImpl(IReflector reflector, ElementDescriptor descriptor) {
				super(reflector,descriptor);
			}			
		}
		
		return new PrivateConstraintFinderImpl(this.backingReflector,this);
	}
	
}
