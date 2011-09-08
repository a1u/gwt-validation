package com.em.validation.rebind.generator.source;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintValidator;

import com.em.validation.rebind.AbstractConstraintDescriptorGenerator;
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
	
	private static class SourceConstraintDescriptorGenerator extends AbstractConstraintDescriptorGenerator<ClassDescriptor> {

		@Override
		protected ClassDescriptor create(ConstraintMetadata metadata) {
			//class descriptor
			ClassDescriptor descriptor = new ClassDescriptor();

			//uuid
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			uuidString = uuidString.replaceAll("\\-","");
			
			//create generation target annotation name
			String generatedConstraintName = ConstraintDescriptionGenerator.INSTANCE.getClassPrefix() + "_" + uuidString;
			String fullGeneratedConstraintName = ConstraintDescriptionGenerator.INSTANCE.getTargetPackage() + "." +generatedConstraintName;
							
			//generate constraint descriptor
			descriptor.setFullClassName(fullGeneratedConstraintName);
			descriptor.setClassName(generatedConstraintName);
			descriptor.setPackageName(ConstraintDescriptionGenerator.INSTANCE.getTargetPackage());
			
			return descriptor;
		}

		@Override
		protected void recurse(ClassDescriptor withDescriptor, ConstraintMetadata metadata) {
			withDescriptor.getDependencies().add(this.getConstraintDescriptor(metadata));
			
		}

		@Override
		protected ClassDescriptor finish(ClassDescriptor withDescriptor, ConstraintMetadata metadata) {

			Set<String> constraintValidatorClassNames = new HashSet<String>();
			for(Class<? extends ConstraintValidator<?, ?>> validator : metadata.getValidatedBy()) {
				String className = validator.getName();
				if(validator.isMemberClass()) {
					className = className.replaceAll("\\$", ".");
				}
				constraintValidatorClassNames.add(className);
			}			
			
			String annotationName = metadata.getName();
			String annotationSimpleName = metadata.getSimpleName();
			String annotationImportName = metadata.getName();
			String annotationType = annotationName + ".class";
			
			//initialize
			Map<String,Object> generatedAnnotationModel = new HashMap<String, Object>();
			
			//create fake annotation data model
			generatedAnnotationModel.put("targetPackage", ConstraintDescriptionGenerator.INSTANCE.getTargetPackage());
			generatedAnnotationModel.put("generatedName", withDescriptor.getClassName());
			generatedAnnotationModel.put("fullGeneratedAnnotationName",withDescriptor.getFullClassName());
			generatedAnnotationModel.put("annotationMetadata",metadata.getMethodMap().values());
			generatedAnnotationModel.put("composedOf", withDescriptor.getDependencies());
			generatedAnnotationModel.put("reportAsSingleViolation", String.valueOf(metadata.isReportAsSingleViolation()));
			generatedAnnotationModel.put("signature",metadata.toString());
			generatedAnnotationModel.put("annotationType", annotationType);
			generatedAnnotationModel.put("annotationImportName", annotationImportName);
			generatedAnnotationModel.put("targetAnnotation",annotationSimpleName);
			generatedAnnotationModel.put("validatedBy",constraintValidatorClassNames);
			
			//finally generate class contents
			withDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/constraint/ConstraintDescriptor.ftl", generatedAnnotationModel));

			return withDescriptor;
		}
		
	}
	
	private SourceConstraintDescriptorGenerator generator = null;
	
	private ConstraintDescriptionGenerator() {
		this.generator = new SourceConstraintDescriptorGenerator();
	}
	
	public ClassDescriptor generateConstraintDescriptor(Annotation annotation, Class<?> elementType) {
		//get annotation metadata
		ConstraintMetadata metadata = ConstraintDescriptionResolver.INSTANCE.getConstraintMetadata(annotation,elementType);
		
		//turn metadata into descriptor
		ClassDescriptor descriptor = this.generator.getConstraintDescriptor(metadata);
		
		//return descriptor
		return descriptor;
	}
		
	public String getTargetPackage() {
		return this.TARGET_PACKAGE;
	}

	public String getClassPrefix() {
		return this.PREFIX;
	}
	
}
