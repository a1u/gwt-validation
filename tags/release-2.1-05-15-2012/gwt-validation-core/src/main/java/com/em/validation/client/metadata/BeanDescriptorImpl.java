package com.em.validation.client.metadata;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
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
