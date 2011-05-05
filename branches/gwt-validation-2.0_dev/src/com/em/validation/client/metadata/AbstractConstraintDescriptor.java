package com.em.validation.client.metadata;

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
