package com.em.validation.rebind.resolve;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Constraint;

import com.em.validation.rebind.metadata.AnnotationMetadata;
import com.em.validation.rebind.metadata.AnnotationMethodMetadata;

public enum AnnotationResolver {

	INSTANCE;
	
	private Map<Annotation, AnnotationMetadata> metadataCache = new HashMap<Annotation, AnnotationMetadata>();
	
	private AnnotationResolver() {
		
	}
	
	public AnnotationMetadata getAnnotationMetadata(Annotation annotation) {
		
		//create annotation metadata
		AnnotationMetadata metadata = this.metadataCache.get(annotation); 
				
		//if the cache misses, generate
		if(metadata == null) {
			metadata = new AnnotationMetadata();
			
			//create empty annotation metadata
			//annotation names
			metadata.setName(annotation.annotationType().getName());
			metadata.setSimpleName(annotation.annotationType().getSimpleName());
					
			//create annotation method metadata
			for(Method method : annotation.annotationType().getDeclaredMethods()) {
				AnnotationMethodMetadata aMeta = new AnnotationMethodMetadata();
				String returnValue = this.createReturnValueAsString(method, annotation);
				
				//get return type
				String returnType = method.getReturnType().getSimpleName();
				if(method.getReturnType().getComponentType() != null) {
					returnType = method.getReturnType().getComponentType().getSimpleName() + "[]";
				}
				aMeta.setReturnType(returnType);
				
				//set the method name and return value
				aMeta.setMethodName(method.getName());
				aMeta.setReturnValue(returnValue);		
				
				//save metadata for use by generator
				metadata.getMethodMap().put(aMeta.getMethodName(), aMeta);
			}
			
			//constraint 
			Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
			metadata.getValidatedBy().addAll(Arrays.asList(constraint.validatedBy()));
			
			//scope
			
			//target element types
		}
		
		return metadata;
	}
	
	/**
	 * Takes an annotation and a method and turns it into the string representation of what
	 * would need to be typed in to a class body to return that value;
	 * 
	 * @param method
	 * @param annotation
	 * @return
	 */
	private String createReturnValueAsString(Method method, Annotation annotation) {
		//invoke method
		Object value = null;

		try {
			value = method.invoke(annotation, new Object[]{});
		} catch (Exception e) {
		} 
		
		//use the return value function to resolve the string.
		return this.createReturnValueAsString(value);
	}
	
	/**
	 * Takes a human readable / string translatable value of the type that would be
	 * found on an annotation and turns it into a string that can be used in a code
	 * generation template.
	 * 
	 * @param method
	 * @param annotation
	 * @return
	 */
	private String createReturnValueAsString(Object value) {
		//return a null value, as a string, so that it is printed as plain null
		//this will preserve whatever the class is doing so that no "inexplicable"
		//changes are made.
		if(value == null) return "null";

		Class<?> returnType = value.getClass();
		Class<?> containedClass = returnType.getComponentType();

		
		StringBuilder output = new StringBuilder();
		if(containedClass == null) {
			if(String.class.equals(returnType)) {
				output.append("\"");
			} 
			if(value instanceof Class<?>) {
				Class<?> clazz = (Class<?>)value;
				output.append(clazz.getName() + ".class");
			} else {				
				output.append(value);
			}
			if(String.class.equals(returnType)) {
				output.append("\"");
			}
		} else {
			//get the array since the return type is of a container 
			Object[] values = (Object[])value;
			
			output.append("new ");
			output.append(containedClass.getSimpleName());
			output.append("[]{");
			int i = 0;
			for(Object v : values) {
				if(i > 0) {
					output.append(",");
				}
				i++;
				if(String.class.equals(containedClass)) {
					output.append("\"");
				} 
				if(v instanceof Class<?>) {
					Class<?> clazz = (Class<?>)v;
					output.append(clazz.getName() + ".class");
				} else {				
					output.append(v);
				}
				if(String.class.equals(containedClass)) {
					output.append("\"");
				}
			}
			output.append("}");
		}		
		
		return output.toString();
	}
	
}
