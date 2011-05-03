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

import com.em.validation.client.validation.AbstractConstraintDescriptor;
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
	
	private final Map<ConstraintDescriptorKey, Map<String,Object>> constraintDescriptorCache = new HashMap<ConstraintDescriptionGenerator.ConstraintDescriptorKey, Map<String,Object>>();
	
	private class ConstraintDescriptorKey {
		private Class<?> targetClass;
		private String propertyName;
		private Annotation annotation;
		
		public ConstraintDescriptorKey(Class<?> targetClass, String propertyName, Annotation annotation) {
			this.targetClass = targetClass;
			this.propertyName = propertyName;
			this.annotation = annotation;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((annotation == null) ? 0 : annotation.hashCode());
			result = prime * result
					+ ((propertyName == null) ? 0 : propertyName.hashCode());
			result = prime * result
					+ ((targetClass == null) ? 0 : targetClass.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ConstraintDescriptorKey other = (ConstraintDescriptorKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (annotation == null) {
				if (other.annotation != null)
					return false;
			} else if (!annotation.equals(other.annotation))
				return false;
			if (propertyName == null) {
				if (other.propertyName != null)
					return false;
			} else if (!propertyName.equals(other.propertyName))
				return false;
			if (targetClass == null) {
				if (other.targetClass != null)
					return false;
			} else if (!targetClass.equals(other.targetClass))
				return false;
			return true;
		}

		private ConstraintDescriptionGenerator getOuterType() {
			return ConstraintDescriptionGenerator.this;
		}
	}	
	
	private ConstraintDescriptionGenerator() {
		
	}
	
	private Map<String,Object> generateTemplateMap(Class<?> targetClass, String propertyName, Annotation annotation) {
		//create key
		ConstraintDescriptorKey key = new ConstraintDescriptorKey(targetClass,propertyName,annotation);
		Map<String,Object> generatedAnnotationModel = this.constraintDescriptorCache.get(key);	
		
		if(generatedAnnotationModel == null) {
			//initialize
			generatedAnnotationModel = new HashMap<String, Object>();
			
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
			String fullGeneratedAnnotationName = this.TARGET_PACKAGE + "." +generatedAnnotationName;
							
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
			generatedAnnotationModel.put("targetPackage", this.TARGET_PACKAGE);
			generatedAnnotationModel.put("generatedName", generatedAnnotationName);
			generatedAnnotationModel.put("fullGeneratedAnnotationName",fullGeneratedAnnotationName);
			generatedAnnotationModel.put("annotationType", annotationType);
			generatedAnnotationModel.put("annotationMetadata",metadata);
			generatedAnnotationModel.put("annotationImportName", annotationImportName);
			generatedAnnotationModel.put("targetAnnotation",annotationSimpleName);
			generatedAnnotationModel.put("validatedBy",constraintValidatorClassNames);
			
			//put the model in the cache
			this.constraintDescriptorCache.put(key, generatedAnnotationModel);
		}
		
		//return the model
		return generatedAnnotationModel;
	}
	
	public String generateConstraintDescription(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor.ftl", dataModel);
	}
	
	public ClassDescriptor generateConstraintClassDescriptor(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
				
		//create the description that the caller will use to do things with the generated class file
		ClassDescriptor description = new ClassDescriptor();
		//generate annotation with template and set into description
		description.setClassContents(this.generateConstraintDescription(targetClass, propertyName, annotation));
		description.setFullClassName((String)dataModel.get("fullGeneratedAnnotationName"));
		description.setClassName((String)dataModel.get("generatedName"));
		
		//return the full class name of the generated annotation
		return description;		
	}
	
	public String getClassName(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		return (String)dataModel.get("fullGeneratedAnnotationName");		
	}
	
	public String getSimpleClassName(Class<?> targetClass, String propertyName, Annotation annotation) {
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		return (String)dataModel.get("generatedName");
	}
	
	public String getAnnotationDeclaration(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_AnnotationDeclaration.ftl", dataModel);
	}
	
	public String getClassBody(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_ClassBody.ftl", dataModel);
	}
	
	public String getConstructor(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_Constructor.ftl", dataModel);
	}
	
	public String getAnnotationInstance(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_getAnnotationInstance.ftl", dataModel);
	}
	
	public String getConstraintValidatorClasses(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_getConstraintValidatorClasses.ftl", dataModel);
	}
	
	public String getGroups(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_getGroups.ftl", dataModel);
	}
	
	public String getPayload(Class<?> targetClass, String propertyName, Annotation annotation) {
		//get the data model
		Map<String,Object> dataModel = this.generateTemplateMap(targetClass, propertyName, annotation);
		//return the class body
		return TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor_getPayload.ftl", dataModel);
	}
	
	public Class<?> getParentClass() {
		return AbstractConstraintDescriptor.class;
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
		
		//return a null value, as a string, so that it is printed as plain null
		//this will preserve whatever the class is doing so that no "inexplicable"
		//changes are made
		if(value == null) return "null";
		
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
				if(i > 1) {
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
	
	public String getTargetPackage() {
		return this.TARGET_PACKAGE;
	}
}
