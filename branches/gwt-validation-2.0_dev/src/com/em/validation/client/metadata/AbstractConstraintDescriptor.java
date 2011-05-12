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


import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Payload;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

public abstract class AbstractConstraintDescriptor<T extends Annotation> implements ConstraintDescriptor<T> {

	/**
	 * The annotation that will be set by the implementing class
	 * 
	 */
	protected T annotation = null;
	
	/**
	 * Set of composing constraints
	 * 
	 */
	protected Set<ConstraintDescriptor<?>> composedOf = new HashSet<ConstraintDescriptor<?>>();
	
	/**
	 * If this should be reported as a single violation
	 * 
	 */
	protected boolean reportAsSingleViolation = false;
	
	/**
	 * The property map that will be populated by the implementing class
	 * 
	 */
	protected Map<String,Object> propertyMap = new HashMap<String,Object>();
	
	@Override
	public T getAnnotation() {
		return this.annotation;
	}	
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.propertyMap;
	}
	
	public AbstractConstraintDescriptor() {
		init();
	}
	
	/**
	 * Override for construction
	 * 
	 */
	public abstract void init();
	
	@Override
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return this.composedOf;
	}
	
	@Override
	public boolean isReportAsSingleViolation() {
		return this.reportAsSingleViolation;
	}
	
	@Override
	public Set<Class<?>> getGroups() {
		Set<Class<?>> result = new HashSet<Class<?>>();
		result.add(Default.class);
		Class<?>[] groups = (Class<?>[])this.getAttributes().get("groups");
		if(groups != null && groups.length > 0) {
			result.clear();
			result.addAll(Arrays.asList(groups));
		}
		return result;
	}

	@Override
	public Set<Class<? extends Payload>> getPayload() {
		Set<Class<? extends Payload>> result = new HashSet<Class<? extends Payload>>();
		@SuppressWarnings("unchecked")
		Class<? extends Payload>[] payloads = (Class<? extends Payload>[])this.getAttributes().get("payload");
		if(payloads != null && payloads.length > 0) {
			result.addAll(Arrays.asList(payloads));
		}
		return result;
	}

}
