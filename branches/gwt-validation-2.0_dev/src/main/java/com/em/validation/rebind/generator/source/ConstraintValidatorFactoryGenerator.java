package com.em.validation.rebind.generator.source;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
		Set<Class<?>> scannedValidators = ClassScanner.INSTANCE.getConstraintValidatorClasses();
		
		Set<String> constraintValidators = new LinkedHashSet<String>();
		
		for(Class<?> validator : scannedValidators) {
			if(validator.isMemberClass()) {
				String memberClass = validator.getName();
				memberClass = memberClass.replaceAll("\\$", ".");
				constraintValidators.add(memberClass);
			} else if(validator.isAnonymousClass()) {
				//nothing to do?
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
