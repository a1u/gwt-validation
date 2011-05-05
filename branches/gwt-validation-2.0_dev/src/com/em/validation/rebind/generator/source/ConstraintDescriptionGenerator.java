package com.em.validation.rebind.generator.source;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintValidator;

import com.em.validation.client.metadata.AbstractConstraintDescriptor;
import com.em.validation.rebind.keys.ConstraintDescriptorKey;
import com.em.validation.rebind.metadata.AnnotationMetadata;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.resolve.AnnotationResolver;
import com.em.validation.rebind.template.TemplateController;

/**
 * Lazy singleton for generating annotations.
 * 
 * @author chris
 *
 */
public enum ConstraintDescriptionGenerator {

	INSTANCE;
		
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.constraints";
	
	private final Map<ConstraintDescriptorKey, Map<String,Object>> constraintDescriptorCache = new HashMap<ConstraintDescriptorKey, Map<String,Object>>();
	
	private ConstraintDescriptionGenerator() {
		
	}
	
	private Map<String,Object> generateTemplateMap(Class<?> targetClass, String propertyName, Annotation annotation) {
		//create key
		ConstraintDescriptorKey key = new ConstraintDescriptorKey(targetClass,propertyName,annotation);
		Map<String,Object> generatedAnnotationModel = this.constraintDescriptorCache.get(key);	
		
		if(generatedAnnotationModel == null) {
			//initialize
			generatedAnnotationModel = new HashMap<String, Object>();
			
			//get annotation metadata
			AnnotationMetadata metadata = AnnotationResolver.INSTANCE.getAnnotationMetadata(annotation);
			
			//annotation names
			String annotationName = metadata.getName();
			String annotationSimpleName = metadata.getSimpleName();
			String annotationImportName = metadata.getName();
			
			//uuid
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			uuidString = uuidString.replaceAll("\\-","");
			
			//create generation target annotation name
			String generatedAnnotationName = targetClass.getSimpleName() + "_" + propertyName + "_" + annotationSimpleName + "_" + uuidString;
			String annotationType = annotationName + ".class";
			String fullGeneratedAnnotationName = this.TARGET_PACKAGE + "." +generatedAnnotationName;
							
			Set<String> constraintValidatorClassNames = new HashSet<String>();
			for(Class<? extends ConstraintValidator<?, ?>> validator : metadata.getValidatedBy()) {
				constraintValidatorClassNames.add(validator.getClass().getName() + ".class");
			}			
					
			//create fake annotation data model
			generatedAnnotationModel.put("targetPackage", this.TARGET_PACKAGE);
			generatedAnnotationModel.put("generatedName", generatedAnnotationName);
			generatedAnnotationModel.put("fullGeneratedAnnotationName",fullGeneratedAnnotationName);
			generatedAnnotationModel.put("annotationType", annotationType);
			generatedAnnotationModel.put("annotationMetadata",metadata.getMethodMap().values());
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
	
	public String getTargetPackage() {
		return this.TARGET_PACKAGE;
	}
}
