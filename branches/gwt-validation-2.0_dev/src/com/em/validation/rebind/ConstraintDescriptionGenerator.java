package com.em.validation.rebind;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;

import com.em.validation.rebind.metadata.AnnotationMethodMetadata;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.dev.util.collect.HashSet;

/**
 * Lazy singleton for generating annotations.
 * 
 * @author chris
 *
 */
public enum ConstraintDescriptionGenerator {

	INSTANCE;
		
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".reflector.generated.constraints";
	
	private ConstraintDescriptionGenerator() {
			
		
	}
	
	public ClassDescriptor generateConstraintAnnotation(Class<?> targetClass, String propertyName, Annotation annotation) {
		
		//annotation names
		String annotationName = annotation.annotationType().getName();
		String annotationSimpleName = annotation.annotationType().getSimpleName();
		String annotationImportName = annotation.annotationType().getName();
		
		//uuid
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		uuidString = uuidString.replaceAll("\\-","");
		
		//create generation target annotation name
		String generatedAnnotationName = targetClass.getSimpleName() + "_" + propertyName + "_" + annotationSimpleName + "_" + uuidString;
		String annotationType = annotationName + ".class";
		String fullGeneratedAnnotationName = TARGET_PACKAGE + "." +generatedAnnotationName;
						
		//create annotation method metadata
		Set<AnnotationMethodMetadata> metadata = new LinkedHashSet<AnnotationMethodMetadata>();
		for(Method method : annotation.annotationType().getDeclaredMethods()) {
			AnnotationMethodMetadata aMeta = new AnnotationMethodMetadata();
			String returnValue = this.createReturnValue(method, annotation);
			
			//get return type
			String returnType = method.getReturnType().getSimpleName();
			if(method.getReturnType().getComponentType() != null) {
				returnType = method.getReturnType().getComponentType().getSimpleName() + "[]";
			}
			aMeta.setReturnType(returnType);
			
			//set the method name and return value
			aMeta.setMethodName(method.getName());
			aMeta.setReturnValue(returnValue);		
			
			//save metadat for use by generator
			metadata.add(aMeta);
		}
		
		Set<String> constraintValidatorClassNames = new HashSet<String>();
		Constraint constraint = annotation.annotationType().getAnnotation(Constraint.class);
		for(Class<? extends ConstraintValidator<?, ?>> validator : constraint.validatedBy()) {
			constraintValidatorClassNames.add(validator.getClass().getName() + ".class");
		}			
				
		//create fake annotation data model
		Map<String,Object> generatedAnnotationModel = new HashMap<String, Object>();
		generatedAnnotationModel.put("targetPackage", this.TARGET_PACKAGE);
		generatedAnnotationModel.put("generatedName", generatedAnnotationName);
		generatedAnnotationModel.put("annotationType", annotationType);
		generatedAnnotationModel.put("annotationMetadata",metadata);
		generatedAnnotationModel.put("annotationImportName", annotationImportName);
		generatedAnnotationModel.put("targetAnnotation",annotationSimpleName);
		generatedAnnotationModel.put("validatedBy",constraintValidatorClassNames);
				
		//create the description that the caller will use to do things with the generated class file
		ClassDescriptor description = new ClassDescriptor();
		//generate annotation with template and set into description
		description.setClassContents(TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor.ftl", generatedAnnotationModel));
		description.setFullClassName(fullGeneratedAnnotationName);
		description.setClassName(generatedAnnotationName);
		
		//return the full class name of the generated annotation
		return description;		
	}
	
	private String createReturnValue(Method method, Annotation annotation) {
		
		StringBuilder output = new StringBuilder();
		
		Class<?> returnType = method.getReturnType();
		Class<?> containedClass = returnType.getComponentType();
		
		//invoke method
		Object value = null;
		
		try {
			value = method.invoke(annotation, new Object[]{});
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		//return a null value
		if(value == null) return null;
		
		if(containedClass == null) {
			if(String.class.equals(returnType)) {
				output.append("\"");
			} 
			output.append(value);
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
				if(i > 1) {
					output.append(",");
				}
				i++;
				if(String.class.equals(containedClass)) {
					output.append("\"");
				} 
				output.append(v);
				if(String.class.equals(containedClass)) {
					output.append("\"");
				}
			}
			output.append("}");
		}		
		
		return output.toString();
	}
	
	public String getTargetPackage() {
		return this.TARGET_PACKAGE;
	}
}
