package com.em.validation.rebind.generator.source;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.resolve.PropertyResolver;
import com.em.validation.rebind.template.TemplateController;

public enum ReflectorGenerator {

	INSTANCE;
	
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.reflector";
	
	public ClassDescriptor getReflectorDescirptions(Class<?> targetClass) {
		//get the runtime reflector (usful for some metadata)
		IReflector<?> runtimeReflector = ReflectorFactory.INSTANCE.getReflector(targetClass);
		
		//target of generation
		String targetPackage = this.TARGET_PACKAGE;

		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		uuidString = uuidString.replaceAll("\\-","");
		
		//get the basic name of the target class and generate the reflector class name, add uuid to avoid colisions with 
		//classes from different trees (and groups) that may have otherwise stomped on each other
		String concreteClassName = targetClass.getSimpleName() + "Reflector_" + uuidString;
		
		//the source listings
		ClassDescriptor reflectorDescriptor = new ClassDescriptor();
	
		//set up imports
		List<String> imports = new ArrayList<String>();
		imports.add(targetClass.getName());

		//the list of properties
		Map<String,PropertyMetadata> metadataMap = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
		
		//list of cascaded properties
		Set<String> cascadedProperties = new LinkedHashSet<String>();

		//generate class descriptors for each metadata on each annotation
		for(PropertyMetadata propertyMetadata : metadataMap.values()) {
			//generate for each targetClass, property, annotation
			for(Annotation annotation : propertyMetadata.getAnnotationInstances()) {
				ClassDescriptor descriptor = ConstraintDescriptionGenerator.INSTANCE.generateConstraintDescriptor(annotation,propertyMetadata.getReturnType());
				propertyMetadata.getConstraintDescriptorClasses().add(descriptor.getClassName());
				reflectorDescriptor.getDependencies().add(descriptor);
			}
			
			if(runtimeReflector.isCascaded(propertyMetadata.getName())) {
				cascadedProperties.add(propertyMetadata.getName());
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
		templateDataModel.put("cascades",cascadedProperties);

		//create class descriptor from generated code
		reflectorDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/reflector/ReflectorImpl.ftl", templateDataModel));
		reflectorDescriptor.setClassName(concreteClassName);
		reflectorDescriptor.setFullClassName(targetPackage + "." + concreteClassName);
		reflectorDescriptor.setPackageName(this.TARGET_PACKAGE);
		
		return reflectorDescriptor;
	}
}
