package com.em.validation.rebind.resolve;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.reflector.factory.RuntimeConstraintDescriptorFactory;


/**
 * Get all of the constraints (via constraint descriptors) of a given class.
 * 
 * @author chris
 *
 */
public enum ConstraintResolver {

	INSTANCE;
	
	private ConstraintResolver() {
		
	}
	
	public Map<String,Set<ConstraintDescriptor<?>>> getConstraintDescriptors(Class<?> targetClass) {
		Map<String,Set<ConstraintDescriptor<?>>> results = new HashMap<String, Set<ConstraintDescriptor<?>>>();
		Map<String,PropertyMetadata> propertyMetadata = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
		for(String propertyName : propertyMetadata.keySet()) {
			results.put(propertyName, this.getConstraintsForProperty(targetClass, propertyName));
		}		
		return results;
	}
	
	public Set<ConstraintDescriptor<?>> getConstraintsForProperty(Class<?> targetClass, String propertyName) {
		Set<ConstraintDescriptor<?>> descriptors = new LinkedHashSet<ConstraintDescriptor<?>>();
		PropertyMetadata property = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass, propertyName);
		for(Annotation annotation : property.getAnnotationInstances()) {
			ConstraintMetadata metadata = AnnotationResolver.INSTANCE.getAnnotationMetadata(annotation);
			ConstraintDescriptor<?> descriptor = RuntimeConstraintDescriptorFactory.INSTANCE.getConstraintDescriptor(metadata);
			descriptors.add(descriptor);
		}		
		return descriptors;
	}
	
}
