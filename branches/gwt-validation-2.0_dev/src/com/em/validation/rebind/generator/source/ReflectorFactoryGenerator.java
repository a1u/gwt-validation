package com.em.validation.rebind.generator.source;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.ReflectorMetadata;
import com.em.validation.rebind.scan.ConstrainedClassScanner;
import com.em.validation.rebind.template.TemplateController;

public enum ReflectorFactoryGenerator {
	
	INSTANCE;
	
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.factory";
	private final String CLASS_NAME = "GeneratedReflectorFactory";
	
	private ReflectorFactoryGenerator() {
		
	}
	
	public String getFullClassName() {
		return this.TARGET_PACKAGE + "." + this.CLASS_NAME;
	}
	
	public ClassDescriptor getReflectorFactoryDescriptors() {
		//base class descriptor
		ClassDescriptor factoryDescriptor = new ClassDescriptor();
		factoryDescriptor.setClassName(this.CLASS_NAME);
		factoryDescriptor.setFullClassName(this.TARGET_PACKAGE + "." + factoryDescriptor.getClassName());
		factoryDescriptor.setPackageName(this.TARGET_PACKAGE);
		
		//add annotation instance factory
		factoryDescriptor.getDependencies().add(AnnotationInstanceFactoryGenerator.INSTANCE.getAnnotationFactoryDescriptor());
		
		//create reflector metadata
		Set<Class<?>> constrainedClasses = ConstrainedClassScanner.INSTANCE.getConstrainedClasses();
		Set<ReflectorMetadata> metadata = new LinkedHashSet<ReflectorMetadata>();
		
		//get precursor/dependency classes
		for(Class<?> targetClass : constrainedClasses) {
			ClassDescriptor reflectorDescriptor = ReflectorGenerator.INSTANCE.getReflectorDescirptions(targetClass);
			factoryDescriptor.getDependencies().add(reflectorDescriptor);
			
			ReflectorMetadata rMeta = new ReflectorMetadata();
			rMeta.setReflectorClass(reflectorDescriptor.getFullClassName());
			rMeta.setTargetClass(targetClass.getName());
			if(targetClass.getSuperclass() != null && !Object.class.equals(targetClass.getSuperclass())) {
				rMeta.setSuperClass(targetClass.getSuperclass().getName());
			}
			for(Class<?> iface : targetClass.getInterfaces()) {
				rMeta.getReflectorInterfaces().add(iface.getName());
			}	
			
			metadata.add(rMeta);
		}
		
		//prepare metadata map
		Map<String,Object> reflectorFactoryModel = new HashMap<String, Object>();
		reflectorFactoryModel.put("reflectorMetadata", metadata);
		reflectorFactoryModel.put("targetPackage", this.TARGET_PACKAGE);
		reflectorFactoryModel.put("className", this.CLASS_NAME);

		//generate from template
		factoryDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/factory/GeneratedReflectorFactory.ftl", reflectorFactoryModel));
		
		return factoryDescriptor;
	}

}
