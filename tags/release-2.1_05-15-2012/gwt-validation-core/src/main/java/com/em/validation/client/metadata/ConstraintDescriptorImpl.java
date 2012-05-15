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


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Describes the basic functions of the ConstraintDescriptor as implemented for both the gwt client and the runtime reflection mode.
 * 
 * @author chris
 *
 * @param <T>
 */
public class ConstraintDescriptorImpl<T extends Annotation> implements ConstraintDescriptor<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The annotation that will be set by the implementing class
	 * 
	 */
	protected transient T annotation = null;
	
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
	protected transient Map<String,Object> propertyMap = new HashMap<String,Object>();
	
	/**
	 * The list of classes that implement validations for this constraint descriptor
	 * 
	 */
	protected transient List<Class<? extends ConstraintValidator<T, ?>>> validatedBy = new ArrayList<Class<? extends ConstraintValidator<T, ?>>>();
	
	/**
	 * Construct a new abstract constraint descriptor
	 */
	public ConstraintDescriptorImpl() {
		init();
	}
	
	/**
	 * Override for construction tasks.  On the instances used by the generated code the constructor cannot be used but the
	 * init can be used to make the required map objects.
	 */
	public void init() {
		
	}
	
	/**
	 * Use this for reflective construction.  This is because we need a way to inject reflectively derrived values WITHOUT
	 * compromising the serialization mechanism.
	 * 
	 * @param reportAsSingleViolation
	 * @param propertyMap
	 * @param validatedBy
	 */
	public void init(T annotation, boolean reportAsSingleViolation, Map<String,Object> propertyMap, List<Class<? extends ConstraintValidator<T, ?>>> validatedBy) {
		this.annotation = annotation;
		this.reportAsSingleViolation = reportAsSingleViolation;
		this.propertyMap = propertyMap;
		this.validatedBy = validatedBy;
	}

	@Override
	public T getAnnotation() {
		return this.annotation;
	}	
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.propertyMap;
	}
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.annotation == null) ? 0 : this.annotation.hashCode());
		result = prime * result + (this.reportAsSingleViolation ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}			
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		if(!(obj instanceof ConstraintDescriptor)) {
			return false;
		}
		
		ConstraintDescriptor<?> other = (ConstraintDescriptor<?>) obj;
		if (annotation == null) {
			if (other.getAnnotation() != null)
				return false;
		}// else if (!annotation.equals(other.getAnnotation()))
		//	return false;
		if (composedOf == null) {
			if (other.getComposingConstraints() != null)
				return false;
		} else if (!composedOf.equals(other.getComposingConstraints()))
			return false;
		if (propertyMap == null) {
			if (other.getAttributes() != null)
				return false;
		}// else if (!propertyMap.equals(other.getAttributes()))
		//	return false;
		if (reportAsSingleViolation != other.isReportAsSingleViolation())
			return false;
		return true;
	}	
	
	@Override 
	public String toString() {
		return this.getAnnotation().toString();
	}

	@Override
	public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
		return new ArrayList<Class<? extends ConstraintValidator<T,?>>>(this.validatedBy);
	}
}
