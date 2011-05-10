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
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.resolve.ConstraintDescriptionResolver;
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
		//get annotation metadata
		ConstraintMetadata metadata = ConstraintDescriptionResolver.INSTANCE.getConstraintMetadata(annotation);
		
		return this.getDescriptorFromMetadata(metadata);
	}
	
	private ClassDescriptor getDescriptorFromMetadata(ConstraintMetadata metadata) {
		ClassDescriptor descriptor = this.descriptorCache.get(metadata.getInstance().toString());
		if(descriptor == null) {
			//class descriptor
			descriptor = new ClassDescriptor();

			//initialize
			Map<String,Object> generatedAnnotationModel = new HashMap<String, Object>();
			
			//annotation names
			String annotationName = metadata.getName();
			String annotationSimpleName = metadata.getSimpleName();
			String annotationImportName = metadata.getName();
			
			//uuid
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			uuidString = uuidString.replaceAll("\\-","");
			
			//create generation target annotation name
			String generatedConstraintName = this.PREFIX + "_" + uuidString;
			String annotationType = annotationName + ".class";
			String fullGeneratedConstraintName = this.TARGET_PACKAGE + "." +generatedConstraintName;
							
			Set<String> constraintValidatorClassNames = new HashSet<String>();
			for(Class<? extends ConstraintValidator<?, ?>> validator : metadata.getValidatedBy()) {
				constraintValidatorClassNames.add(validator.getClass().getName());
			}			
			
			//push into cache
			this.descriptorCache.put(metadata.getInstance().toString(), descriptor);

			//generate constraint descriptor
			descriptor.setFullClassName(fullGeneratedConstraintName);
			descriptor.setClassName(generatedConstraintName);
			descriptor.setPackageName(this.TARGET_PACKAGE);
			
			//get the generated code for the constrageneratedAnnotationNameints of the composing metadata, if it exists
			for(ConstraintMetadata subMetadata : metadata.getComposedOf()) {
				descriptor.getDependencies().add(this.getDescriptorFromMetadata(subMetadata));
			}
			
			//create fake annotation data model
			generatedAnnotationModel.put("targetPackage", this.TARGET_PACKAGE);
			generatedAnnotationModel.put("generatedName", generatedConstraintName);
			generatedAnnotationModel.put("fullGeneratedAnnotationName",fullGeneratedConstraintName);
			generatedAnnotationModel.put("annotationType", annotationType);
			generatedAnnotationModel.put("annotationMetadata",metadata.getMethodMap().values());
			generatedAnnotationModel.put("composedOf", descriptor.getDependencies());
			generatedAnnotationModel.put("reportAsSingleViolation", String.valueOf(metadata.isReportAsSingleViolation()));
			generatedAnnotationModel.put("annotationImportName", annotationImportName);
			generatedAnnotationModel.put("targetAnnotation",annotationSimpleName);
			generatedAnnotationModel.put("validatedBy",constraintValidatorClassNames);
			
			//finally generate class contents
			descriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor.ftl", generatedAnnotationModel));
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
