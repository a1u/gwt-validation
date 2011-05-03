package com.em.validation.client.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

public abstract class AbstractConstraintDescriptor<T extends Annotation> implements ConstraintDescriptor<T> {

	/**
	 * The annotation that will be set by the implementing class
	 * 
	 */
	protected T annotation = null;
	
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
	
	@Override
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return null;
	}
	
	@Override
	public boolean isReportAsSingleViolation() {
		return true;
	}
}
