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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.ConstraintPropertyMetadata;
import com.em.validation.rebind.resolve.ConstraintDescriptionResolver;
import com.em.validation.rebind.scan.ConstrainedClassScanner;
import com.em.validation.rebind.template.TemplateController;

public enum AnnotationInstanceFactoryGenerator {

	INSTANCE;
	
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.factory";
	private final String PREFIX = "AnnotationInstanceFactory";
	
	private AnnotationInstanceFactoryGenerator() {
		
	}
	
	public ClassDescriptor getAnnotationFactoryDescriptor() {
		
		//create empty set
		ClassDescriptor outerFactoryDescriptor = new ClassDescriptor();
		
		//get all constrained classes
		Set<Class<?>> constrainedClasses = ConstrainedClassScanner.INSTANCE.getConstrainedClasses();
		
		//a place to store all of the constraint metadata
		Set<ConstraintMetadata> constraintMetadataSet = new LinkedHashSet<ConstraintMetadata>();
		
		//a place to store all of the constraint metadata
		Map<Class<?>,Set<ConstraintMetadata>> annotationClassToMetadataMap = new LinkedHashMap<Class<?>, Set<ConstraintMetadata>>();	
		Map<Class<?>,Set<ConstraintPropertyMetadata>> annotationMethodMetadata = new LinkedHashMap<Class<?>, Set<ConstraintPropertyMetadata>>();
		
		//and, finally, a map of the constraint class names to the annotation instance names
		Map<String,String> factoryMap = new LinkedHashMap<String, String>();
		
		//get all of the metadata
		for(Class<?> targetClass : constrainedClasses) {
			constraintMetadataSet.addAll(ConstraintDescriptionResolver.INSTANCE.getAllMetadata(targetClass));
		}
		
		//break down the metadata into signatures and constraints
		for(ConstraintMetadata metadata : constraintMetadataSet) {
			//set of constraint metadata
			Set<ConstraintMetadata> localSet = annotationClassToMetadataMap.get(metadata.getInstance().annotationType());
			//place new instance in map, if null
			if(localSet == null) {
				localSet = new LinkedHashSet<ConstraintMetadata>();
				annotationClassToMetadataMap.put(metadata.getInstance().annotationType(), localSet);
			}
			//populate local set with the metadata instance
			localSet.add(metadata);
			
			//set of method names
			Set<ConstraintPropertyMetadata> methodNames = annotationMethodMetadata.get(metadata.getInstance().annotationType());
			//place new instance in map, if null
			if(methodNames == null) {
				methodNames = new LinkedHashSet<ConstraintPropertyMetadata>();
				annotationMethodMetadata.put(metadata.getInstance().annotationType(),methodNames);
			}
			methodNames.addAll(metadata.getMethodMap().values());
		}
		
		//at this point we have a map of all of the values that we really need to start generating annotation instances
		for(Class<?> annotationClass : annotationClassToMetadataMap.keySet()) {
			Set<ConstraintMetadata> constraints = annotationClassToMetadataMap.get(annotationClass);
			Set<ConstraintPropertyMetadata> methods = annotationMethodMetadata.get(annotationClass);

			//create generated name
			//uuid
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			uuidString = uuidString.replaceAll("\\-","");
			
			//create generation target annotation name
			String generatedFactoryName = this.PREFIX + "_" + uuidString;
			String fullGeneratedFactoryName = this.TARGET_PACKAGE + ".instances." +generatedFactoryName;
			
			//target annotation stuff
			String annotationImportName = annotationClass.getName();
			String targetAnnotation = annotationClass.getSimpleName();
			
			//build the template object map
			Map<String,Object> templateMap = new HashMap<String, Object>();
			templateMap.put("constraints",constraints);
			templateMap.put("methods", methods);
			templateMap.put("generatedName",generatedFactoryName);
			templateMap.put("targetPackage", this.TARGET_PACKAGE + ".instances");
			templateMap.put("annotationImportName",annotationImportName);
			templateMap.put("targetAnnotation",targetAnnotation);
			
			//use the template and create and save a new descriptor
			ClassDescriptor descriptor = new ClassDescriptor();
			descriptor.setPackageName(this.TARGET_PACKAGE + ".instances");
			descriptor.setFullClassName(fullGeneratedFactoryName);
			descriptor.setClassName(generatedFactoryName);
			descriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/annotation/ConcreteAnnotationInstanceFactory.ftl", templateMap));
			
			//System.out.println(descriptor.getClassContents() + "\n");

			//add to the factory map to generate the factory factory
			factoryMap.put(annotationImportName, descriptor.getFullClassName());
			
			//add the class descriptor to be the dependency of the outer descriptor
			outerFactoryDescriptor.getDependencies().add(descriptor);
		}
		
		//set data on the class descriptor
		outerFactoryDescriptor.setClassName("AnnotationInstanceFactory");
		outerFactoryDescriptor.setFullClassName(this.TARGET_PACKAGE + "." + outerFactoryDescriptor.getClassName());
		outerFactoryDescriptor.setPackageName(this.TARGET_PACKAGE);
		
		//create the class from the template
		Map<String,Object> factoryTemplateMap = new HashMap<String, Object>();
		factoryTemplateMap.put("factoryMap", factoryMap);
		factoryTemplateMap.put("targetPackage",this.TARGET_PACKAGE);

		//set contents of class descriptor from the template
		outerFactoryDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/annotation/AnnotationInstanceFactory.ftl", factoryTemplateMap));
		
		//System.out.println(outerFactoryDescriptor.getClassContents());
		
		//return set
		return outerFactoryDescriptor;
	}

}
