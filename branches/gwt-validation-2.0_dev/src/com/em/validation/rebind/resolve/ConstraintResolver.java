package com.em.validation.rebind.resolve;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.rebind.metadata.AnnotationMetadata;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.metadata.RuntimeConstraintDescriptor;


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
	
	public Map<String,Set<ConstraintDescriptor<Annotation>>> getConstraintDescriptors(Class<?> targetClass) {
		Map<String,Set<ConstraintDescriptor<Annotation>>> results = new HashMap<String, Set<ConstraintDescriptor<Annotation>>>();
		Map<String,PropertyMetadata> propertyMetadata = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
		for(String propertyName : propertyMetadata.keySet()) {
			results.put(propertyName, this.getConstraintsForProperty(targetClass, propertyName));
		}		
		return results;
	}
	
	public Set<ConstraintDescriptor<Annotation>> getConstraintsForProperty(Class<?> targetClass, String propertyName) {
		Set<ConstraintDescriptor<Annotation>> descriptors = new LinkedHashSet<ConstraintDescriptor<Annotation>>();
		PropertyMetadata property = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass, propertyName);
		for(Annotation annotation : property.getAnnotationInstances()) {
			AnnotationMetadata metadata = AnnotationResolver.INSTANCE.getAnnotationMetadata(annotation);
			ConstraintDescriptor<Annotation> descriptor = new RuntimeConstraintDescriptor<Annotation>(metadata);
			descriptors.add(descriptor);
		}		
		return descriptors;
	}
	
}
