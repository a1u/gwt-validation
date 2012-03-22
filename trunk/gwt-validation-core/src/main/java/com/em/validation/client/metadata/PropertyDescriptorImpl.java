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


import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.reflector.IReflector;

public class PropertyDescriptorImpl extends ProtoDescriptor implements PropertyDescriptor {

	protected String propertyName = "";
	
	public PropertyDescriptorImpl(IReflector reflector, String name) {
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
	
	@Override
	public ConstraintFinder findConstraints() {
		final class PrivatePropertyConstraintFinderImpl extends PropertyConstraintFinderImpl {
			public PrivatePropertyConstraintFinderImpl(IReflector reflector, String propertyName) {
				super(reflector,propertyName);
			}			
		}
		
		return new PrivatePropertyConstraintFinderImpl(this.backingReflector,this.propertyName);
	}

}
