package com.em.validation.rebind.reflector.factory;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.metadata.ConstraintDescriptorDecorator;
import com.em.validation.client.metadata.ConstraintDescriptorImpl;
import com.em.validation.rebind.AbstractConstraintDescriptorGenerator;
import com.em.validation.rebind.metadata.ConstraintMetadata;

public enum RuntimeConstraintDescriptorFactory {
	
	INSTANCE;

	private static class RuntimeConstraintGenerator extends AbstractConstraintDescriptorGenerator<ConstraintDescriptor<?>> {

		private Map<String, ConstraintDescriptorImpl<Annotation>> descriptorCache = new HashMap<String, ConstraintDescriptorImpl<Annotation>>();
		
		@SuppressWarnings("unchecked")
		@Override
		protected ConstraintDescriptor<?> create(ConstraintMetadata metadata) {
			ConstraintDescriptorImpl<Annotation> descriptor = new ConstraintDescriptorImpl<Annotation>();
			
			//get metadata anotation instance
			Annotation annotation = metadata.getInstance();
			String key = annotation.toString() + metadata.getTargetClass().getSimpleName();
						
			if(this.descriptorCache.containsKey(key)) {
				descriptor = this.descriptorCache.get(key);
			} else {				
				//create property map
				Map<String,Object> propertyMap = new HashMap<String,Object>();
				for(Method method : annotation.getClass().getDeclaredMethods()) {
					try {
						Object value = method.invoke(annotation, new Object[]{});
						propertyMap.put(method.getName(), value);
					} catch (Exception ex) {
	
					}
				}			
				
				//create validated map
				List<Class<? extends ConstraintValidator<Annotation, ?>>> validatedBy = new ArrayList<Class<? extends ConstraintValidator<Annotation,?>>>();
				for(Class<? extends ConstraintValidator<?, ?>> cv : metadata.getValidatedBy()) {
					try {
						validatedBy.add((Class<? extends ConstraintValidator<Annotation, ?>>) cv);
					} catch (Exception ex) {
						//do not add if the cast fails
					}
				}				
				
				//initialize descriptor with metadata
				descriptor.init(annotation,
								metadata.isReportAsSingleViolation(),
								propertyMap,
								validatedBy);
	
				//put in cache to block recursion
				this.descriptorCache.put(key, descriptor);				
				
				//now we are free to recurse and build composing constraints
				Set<ConstraintDescriptor<?>> composedOf = new HashSet<ConstraintDescriptor<?>>();
				for(ConstraintMetadata composing : metadata.getComposedOf()) {
					ConstraintDescriptor<?> c = this.create(composing);
					c = this.finish(c, composing);
					composedOf.add(c);				
				}
				descriptor.getComposingConstraints().addAll(composedOf);
			}
			
			return this.finish(descriptor,metadata);
		}

		@Override
		protected void recurse(ConstraintDescriptor<?> withDescriptor, ConstraintMetadata metadata) {
			withDescriptor.getComposingConstraints().add(this.getConstraintDescriptor(metadata));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected ConstraintDescriptor<?> finish(ConstraintDescriptor<?> withDescriptor, ConstraintMetadata metadata) {
			return new ConstraintDescriptorDecorator<Annotation>((ConstraintDescriptor<Annotation>)withDescriptor);
		}
		
	}
	
	private RuntimeConstraintGenerator generator = null;
	
	private RuntimeConstraintDescriptorFactory() {
		this.generator = new RuntimeConstraintGenerator();
	}
	
	public ConstraintDescriptor<?> getConstraintDescriptor(ConstraintMetadata metadata) {
		return generator.getConstraintDescriptor(metadata);
	}
	
}
