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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.ReflectorMetadata;
import com.em.validation.rebind.scan.ClassScanner;
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
		Set<Class<?>> constrainedClasses = ClassScanner.INSTANCE.getConstrainedClasses();
		Set<ReflectorMetadata> metadata = new LinkedHashSet<ReflectorMetadata>();
		
		//get precursor/dependency classes
		for(Class<?> targetClass : constrainedClasses) {
			//get all of the constraint descriptors for the class and use that to build the groups, then use those to create
			//reflectors for themselves
			BeanDescriptor targetBean = DescriptorFactory.INSTANCE.getBeanDescriptor(targetClass);
			for(ConstraintDescriptor<?> tempConstraint : targetBean.findConstraints().getConstraintDescriptors()) {
				for(Class<?> group : tempConstraint.getGroups()) {
					metadata.addAll(this.getReflectorMetadataTreeAsSet(group,factoryDescriptor));
				}
			}
		
			//generate reflector metadata
			metadata.addAll(this.getReflectorMetadataTreeAsSet(targetClass,factoryDescriptor));
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
	
	//only do once
	private Set<Class<?>> lockingSet = new HashSet<Class<?>>();

	private Set<ReflectorMetadata> getReflectorMetadataTreeAsSet(Class<?> targetClass, ClassDescriptor factoryDescriptor) {

		Set<ReflectorMetadata> metadata = new LinkedHashSet<ReflectorMetadata>();

		//if Object, Annotation, or null, return empty set
		if(Object.class.equals(targetClass) || Annotation.class.equals(targetClass) || targetClass == null || targetClass.isAnnotation()) {
			return metadata;
		}
		
		//don't do this if it has already been done
		if(this.lockingSet.contains(targetClass)) return metadata;
		
		//recurse to superclass
		metadata.addAll(this.getReflectorMetadataTreeAsSet(targetClass.getSuperclass(), factoryDescriptor));
		
		//recurse to interface
		for(Class<?> iface : targetClass.getInterfaces()) {
			metadata.addAll(this.getReflectorMetadataTreeAsSet(iface, factoryDescriptor));
		}
		
		//generate class descriptor
		ClassDescriptor descriptor = ReflectorGenerator.INSTANCE.getReflectorDescirptions(targetClass);
		factoryDescriptor.getDependencies().add(descriptor);
		
		//get reflector metadata object for the generated group reflector
		ReflectorMetadata rMeta = new ReflectorMetadata();
		rMeta.setReflectorClass(descriptor.getFullClassName());
		
		//the target class must be manipulated to not have a $ (must not be binary name)
		String targetClassString = targetClass.getName();
		if(targetClassString != null && !targetClassString.isEmpty()) {
			targetClassString = targetClassString.replaceAll("\\$", ".");
		}		
		rMeta.setTargetClass(targetClassString);

		if(targetClass.getSuperclass() != null && !Object.class.equals(targetClass.getSuperclass())) {
			rMeta.setSuperClass(targetClass.getSuperclass().getName());
		}
		for(Class<?> iface : targetClass.getInterfaces()) {
			rMeta.getReflectorInterfaces().add(iface.getName());
		}
		
		//add class to locking set
		this.lockingSet.add(targetClass);
		
		metadata.add(rMeta);
		
		return metadata;
	}
	
	/**
	 * Method for clearing all metadata for a clean run
	 * 
	 */
	public void clear() {
		this.lockingSet.clear();
	}

}
