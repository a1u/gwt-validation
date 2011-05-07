package com.em.validation.rebind.generator.source;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.resolve.PropertyResolver;
import com.em.validation.rebind.template.TemplateController;

public enum ReflectorGenerator {

	INSTANCE;
	
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.reflector";
	
	public ClassDescriptor getReflectorDescirptions(Class<?> targetClass) {
		//target of generation
		String targetPackage = this.TARGET_PACKAGE;

		//get the basic name of the target class and generate the reflector class name
		String concreteClassName = targetClass.getSimpleName() + "Reflector";
		
		//the source listings
		ClassDescriptor reflectorDescriptor = new ClassDescriptor();
	
		//set up imports
		List<String> imports = new ArrayList<String>();
		imports.add(targetClass.getName());

		//the list of properties
		Map<String,PropertyMetadata> metadataMap = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);

		//generate class descriptors for each metadata on each annotation
		for(PropertyMetadata propertyMetadata : metadataMap.values()) {
			//generate for each targetClass, property, annotation
			for(Annotation annotation : propertyMetadata.getAnnotationInstances()) {
				//ClassDescriptor descriptor = ConstraintDescriptionGenerator.INSTANCE.generateConstraintClassDescriptor(targetClass, propertyMetadata.getName(), annotation);
				ClassDescriptor descriptor = ConstraintDescriptionGenerator.INSTANCE.generateConstraintDescriptor(annotation);
				propertyMetadata.getAnnotations().add(descriptor.getClassName());
				reflectorDescriptor.getDependencies().add(descriptor);
			}
		}
		
		//create data model
		Map<String,Object> templateDataModel = new HashMap<String, Object>();
		templateDataModel.put("concreteClassName", concreteClassName);
		templateDataModel.put("importList", imports);
		templateDataModel.put("properties", metadataMap.values());
		templateDataModel.put("reflectionTargetName", targetClass.getSimpleName());
		templateDataModel.put("targetPackage", targetPackage);
		templateDataModel.put("generatedConstraintPackage",ConstraintDescriptionGenerator.INSTANCE.getTargetPackage());

		//create class descriptor from generated code
		reflectorDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/reflector/ReflectorImpl.ftl", templateDataModel));
		reflectorDescriptor.setClassName(concreteClassName);
		reflectorDescriptor.setFullClassName(targetPackage + "." + concreteClassName);
		reflectorDescriptor.setPackageName(this.TARGET_PACKAGE);
		
		return reflectorDescriptor;
	}
}
