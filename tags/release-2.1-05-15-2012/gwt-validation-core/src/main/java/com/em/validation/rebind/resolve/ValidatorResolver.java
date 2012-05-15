package com.em.validation.rebind.resolve;

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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;

public enum ValidatorResolver {

	INSTANCE;
	
	private Map<Class<?>, Class<?>> primitiveClasses = new HashMap<Class<?>, Class<?>>();
	
	private ValidatorResolver() {
		//handle boxing/unboxing
		this.primitiveClasses.put(boolean.class, Boolean.class);
		this.primitiveClasses.put(int.class, Integer.class);
		this.primitiveClasses.put(float.class, Float.class);
		this.primitiveClasses.put(double.class, Double.class);
		this.primitiveClasses.put(long.class, Long.class);
		this.primitiveClasses.put(short.class, Short.class);
		this.primitiveClasses.put(byte.class, Byte.class);
		this.primitiveClasses.put(char.class, Character.class);
	}
	
	public Set<Class<?>> getValidatorClassesForAnnotation(Class<?> annotationType, Class<?> elementType) {
		//create empty set
		Set<Class<?>> results = new LinkedHashSet<Class<?>>();
		Set<Class<?>> candidates = new LinkedHashSet<Class<?>>();

		//get toolkit provided validators for built in annotations (in javax.validation.constraints)
		candidates.addAll(BuiltInValidatorHelper.getBuiltInValidators(annotationType));
		
		//get set from the @Constraint annotation
		Constraint constraint = annotationType.getAnnotation(Constraint.class);
		if(constraint != null) {
			candidates.addAll(Arrays.asList(constraint.validatedBy()));
		}

		//grab class wrapper for primitive type
		if(elementType != null && elementType.isPrimitive() && this.primitiveClasses.containsKey(elementType)) {
			elementType = this.primitiveClasses.get(elementType);
		}
				
		//go through the candidate classes, and check the validate method for the right types
		for(Class<?> validator : candidates) {
			//no anonymous classes
			if(validator.isAnonymousClass()) continue;
			//abstract classes don't help either, they can't be initialized and so... don't work for us
			if(Modifier.isAbstract(validator.getModifiers())) continue;
			
			//check isValid method to see if the element type is accepted by that method
			try {
				//changed to getMethods from declaredMethods to fix issues with extended/implemented validators
				for(Method method : validator.getMethods()) { 
					if("isValid".equals(method.getName()) && method.getParameterTypes().length == 2) {
						Class<?> parameterType = method.getParameterTypes()[0];
						
						//exact equality makes a candidate a result
						if(parameterType.equals(elementType)) {
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
		
		//if no results are found: ValidationException for no validators found
		//if(results.size() == 0) {
		//	throw new ValidationException("No validators for constraint " + annotationType.getName() + " on type " + elementType.getName() + " found.");
		//}
		
		//return
		return results;
	}
	
}
