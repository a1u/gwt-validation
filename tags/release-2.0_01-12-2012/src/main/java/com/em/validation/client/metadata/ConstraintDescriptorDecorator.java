package com.em.validation.client.metadata;

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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

public class ConstraintDescriptorDecorator<T extends Annotation> implements ConstraintDescriptor<T> {

	private ConstraintDescriptor<T> descriptor = null;
	
	private ConstraintDescriptorDecorator() {
		
	}
	
	public ConstraintDescriptorDecorator(ConstraintDescriptor<T> descriptor) {
		this();
		
		this.descriptor = descriptor;
	}
	
	@Override
	public T getAnnotation() {
		return this.descriptor.getAnnotation();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.descriptor.getAttributes();
	}

	@Override
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return this.descriptor.getComposingConstraints();
	}

	@Override
	public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
		return this.descriptor.getConstraintValidatorClasses();
	}

	@Override
	public Set<Class<?>> getGroups() {
		return this.descriptor.getGroups();
	}

	@Override
	public Set<Class<? extends Payload>> getPayload() {
		return this.descriptor.getPayload();
	}

	@Override
	public boolean isReportAsSingleViolation() {
		return this.descriptor.isReportAsSingleViolation();
	}

	@Override
	public int hashCode() {
		return this.descriptor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof ConstraintDescriptorDecorator) {
			ConstraintDescriptorDecorator<?> input = (ConstraintDescriptorDecorator<?>)obj;
			return this.descriptor.equals(input.descriptor);
		} 
		return this.descriptor.equals(obj);
	}
	
	@Override
	public String toString() {
		return this.descriptor.toString();
	}
}
