package com.em.validation.rebind.generator.source;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

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
