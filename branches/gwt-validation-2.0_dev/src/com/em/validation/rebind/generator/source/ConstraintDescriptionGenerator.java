package com.em.validation.rebind.generator.source;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintValidator;

import com.em.validation.client.metadata.AbstractConstraintDescriptor;
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
	private final String PREFIX = "ConstraintDescriptor";
	
	private final Map<String, ClassDescriptor> descriptorCache = new LinkedHashMap<String, ClassDescriptor>();
	
	private ConstraintDescriptionGenerator() {
		
	}
	
	public ClassDescriptor generateConstraintDescriptor(Annotation annotation) {
		//System.out.println("Annotation: " + annotation.toString());
		ClassDescriptor descriptor = this.descriptorCache.get(annotation.toString());
		if(descriptor == null) {
			//initialize
			Map<String,Object> generatedAnnotationModel = new HashMap<String, Object>();
			
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
			String generatedAnnotationName = this.PREFIX + "_" + uuidString;
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
			
			//populate descriptor
			//create the description that the caller will use to do things with the generated class file
			descriptor = new ClassDescriptor();
			//generate annotation with template and set into description
			descriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor.ftl", generatedAnnotationModel));
			descriptor.setFullClassName((String)generatedAnnotationModel.get("fullGeneratedAnnotationName"));
			descriptor.setClassName((String)generatedAnnotationModel.get("generatedName"));
			
			//put the model in the cache
			this.descriptorCache.put(annotation.toString(), descriptor);			
		}		
		return descriptor;
	}
		
	public Class<?> getParentClass() {
		return AbstractConstraintDescriptor.class;
	}
	
	public String getTargetPackage() {
		return this.TARGET_PACKAGE;
	}
}
