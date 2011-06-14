package com.em.validation.rebind.resolve;

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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;

import com.em.validation.rebind.scan.ClassScanner;

public enum ValidatorResolver {

	INSTANCE;
	
	private ValidatorResolver() {
		
	}
	
	public Set<Class<?>> getValidatorClassesForAnnotation(Class<?> annotationType, Class<?> elementType) {
		//create empty set
		Set<Class<?>> results = new LinkedHashSet<Class<?>>();
		Set<Class<?>> candidates = new LinkedHashSet<Class<?>>();
		
		//get set from the @Constraint annotation
		Constraint constraint = annotationType.getAnnotation(Constraint.class);
		if(constraint != null) {
			candidates.addAll(Arrays.asList(constraint.validatedBy()));
		}
		
		//get all constraint validators
		Set<Class<? extends ConstraintValidator<?, ?>>> validators = ClassScanner.INSTANCE.getConstraintValidatorClasses();
		
		//check for the type of the initialize method
		for(Class<? extends ConstraintValidator<?, ?>> validator : validators) {
			//check initialize method to see if the annotation type matches the constraint annotation's type
			try {
				Method initialize = validator.getMethod("initialize", new Class<?>[]{annotationType});
				if(initialize != null) {
					candidates.add(validator);
				}
			} catch (Exception ex) {
				//method isn't there, do nothing
			}
		}
		
		//go through the candidate classes, and check the validate method for the right types
		for(Class<?> validator : candidates) {
			//no anonymous classes
			if(validator.isAnonymousClass()) continue;
			//abstract classes don't help either, they can't be initialized and so... don't work for us
			if(Modifier.isAbstract(validator.getModifiers())) continue;
			
			//check isValid method to see if the element type
			try {
				for(Method method : validator.getDeclaredMethods()) { 
					if("isValid".equals(method.getName()) && method.getParameterTypes().length == 2) {
						Class<?> parameterType = method.getParameterTypes()[0];						
						
						//exact equality makes a candidate a result
						if(elementType.equals(parameterType)) {
							results.add(validator);
						} else 											
						//if parameterType is higher on the type hierarchy tree then the elementType it means that 
						//the elementType could be used to call the method and that this candidate is a result
						if(parameterType.isAssignableFrom(elementType)) {
							results.add(validator);
						} 

						//the first match to isValid is the one we should use
						break;
					}
				}
						
			} catch (Exception ex) {
				//method isn't there, do nothing
			}
			
		}		
		
		//return
		return results;
	}
	
}
