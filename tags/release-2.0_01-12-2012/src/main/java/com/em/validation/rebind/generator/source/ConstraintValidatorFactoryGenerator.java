package com.em.validation.rebind.generator.source;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;

import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.scan.ClassScanner;
import com.em.validation.rebind.template.TemplateController;

public enum ConstraintValidatorFactoryGenerator {

	INSTANCE;
	
	private final String BASE_PACKAGE = "com.em.validation.client";
	private final String TARGET_PACKAGE = this.BASE_PACKAGE + ".generated.factory";
	
	private ConstraintValidatorFactoryGenerator() {
		
	}
	
	public ClassDescriptor generateConstraintValidatorFactory() {
		
		ClassDescriptor factoryDescriptor = new ClassDescriptor();
		
		//set class details
		factoryDescriptor.setClassName("GeneratedConstraintValidatorFactory");
		factoryDescriptor.setFullClassName(this.TARGET_PACKAGE + "." + factoryDescriptor.getClassName());
		factoryDescriptor.setPackageName(this.TARGET_PACKAGE);
		
		//set of class names for constraints
		Set<Class<? extends ConstraintValidator<?, ?>>> scannedValidators = ClassScanner.INSTANCE.getConstraintValidatorClasses();
		
		Set<String> constraintValidators = new LinkedHashSet<String>();
		
		for(Class<?> validator : scannedValidators) {
			if(validator.isAnonymousClass()) continue;
			if(Modifier.isAbstract(validator.getModifiers())) continue;
			
			if(validator.isMemberClass()) {
				String memberClass = validator.getName();
				memberClass = memberClass.replaceAll("\\$", ".");
				constraintValidators.add(memberClass);
			} else {
				constraintValidators.add(validator.getName());
			}
		}
		
		//set up map
		Map<String,Object> templateDataModel = new HashMap<String, Object>();
		templateDataModel.put("targetPackage",this.TARGET_PACKAGE);
		templateDataModel.put("constraintValidators", constraintValidators);
		
		//set contents of class descriptor
		factoryDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/validator/GeneratedConstraintValidatorFactory.ftl", templateDataModel));
				
		return factoryDescriptor;
	}
	
}
