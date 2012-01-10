package com.em.validation.client.reflector;

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

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.Scope;

public abstract class AbstractCompiledReflector extends Reflector {

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
				for(IReflector iface : this.reflectorInterfaces) {
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
				for(IReflector iface : this.reflectorInterfaces) {
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
			for(IReflector iface : this.reflectorInterfaces) {
				if(iface != null) {
					results.addAll(iface.declaredOn(scope,property,descriptor));
				}
			}
		}

		return results;
	}	

	
}
