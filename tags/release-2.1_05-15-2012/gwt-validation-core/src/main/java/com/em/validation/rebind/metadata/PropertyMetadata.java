package com.em.validation.rebind.metadata;

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
import java.util.ArrayList;
import java.util.List;

public class PropertyMetadata {


	private List<String> constraintDescriptors = new ArrayList<String>();
	private List<Annotation> annotationInstances = new ArrayList<Annotation>();
	
	private String name;
	private String accessor;
	private String classString;
	
	private Class<?> returnType = null;
	
	private boolean field = true;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccessor() {
		return accessor;
	}
	public void setAccessor(String accessor) {
		this.accessor = accessor;
	}

	public List<String> getConstraintDescriptorClasses() {
		return constraintDescriptors;
	}
	public void setConstraintDescriptorClasse(List<String> constraintDescriptors) {
		this.constraintDescriptors = constraintDescriptors;
	}
	
	public String getClassString() {
		return classString;
	}	
	public void setClassString(String classString) {
		this.classString = classString;
	}
	
	public List<Annotation> getAnnotationInstances() {
		return annotationInstances;
	}
	public void setAnnotationInstances(List<Annotation> annotationInstances) {
		this.annotationInstances = annotationInstances;
	}
	
	public boolean isField() {
		return field;
	}
	public void setField(boolean field) {
		this.field = field;
	}
	
	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
	public Class<?> getReturnType() {
		return returnType;
	}
	
}
