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
public class ConstraintDescriptorImpl<T extends Annotation> implements ConstraintDescriptor<T> {

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
	
	/**
	 * The list of classes that implement validations for this constraint descriptor
	 * 
	 */
	protected List<Class<? extends ConstraintValidator<T, ?>>> validatedBy = new ArrayList<Class<? extends ConstraintValidator<T, ?>>>();
	
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
