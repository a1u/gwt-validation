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
import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.metadata.Scope;

public class ConstraintMetadata {

	/**
	 * Maps the method names to the methods themselves.  Can be used to get the method value.
	 * 
	 */
	private Map<String, ConstraintPropertyMetadata> methodMap = new LinkedHashMap<String, ConstraintPropertyMetadata>();
	
	/**
	 * The name of the annotation (full class name)
	 * 
	 */
	private String name = "";
	
	/**
	 * The simple name (full name minus the package)
	 * 
	 */
	private String simpleName = "";
	
	/**
	 * The possible validators for this constraint
	 * 
	 */
	private Set<Class<? extends ConstraintValidator<?, ?>>> validatedBy = new LinkedHashSet<Class<? extends ConstraintValidator<?, ?>>>();
	
	
	/**
	 * All of the scopes that the annotation is in
	 * 
	 */
	private Set<Scope> scope = new LinkedHashSet<Scope>();
	
	
	/**
	 * Target class of the constraint
	 * 
	 */
	private Class<?> targetClass = null;
	
	/**
	 * The types that the constraint can reside on
	 * 
	 */
	private Set<ElementType> elementTypes = new LinkedHashSet<ElementType>();


	/**
	 * The actual annotation instance that backs this metadata
	 * 
	 */
	private Annotation instance = null;
	
	/**
	 * A list of the constraints that the constraint is composed of
	 * 
	 */
	private Set<ConstraintMetadata> composedOf = new LinkedHashSet<ConstraintMetadata>();
	
	/**
	 * Should this report any violations as multiple violations or all as one?
	 *  true = one violation and none other
	 *  false = report any violation individually and independently of the others
	 */
	private boolean reportAsSingleViolation = false;
	
	/**
	 * Stores the overrides for properties
	 * 
	 */
	private Map<String,OverridesMetadata> overridesMap = new LinkedHashMap<String, OverridesMetadata>();
	
	/**
	 * This is the set of element types that this constraint was observed declared on
	 * 
	 */
	private Set<ElementType> declaredOn = new HashSet<ElementType>();
	
	public Map<String, ConstraintPropertyMetadata> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(Map<String, ConstraintPropertyMetadata> methodMap) {
		this.methodMap = methodMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public Set<Class<? extends ConstraintValidator<?, ?>>> getValidatedBy() {
		return validatedBy;
	}


	public void setValidatedBy(Set<Class<? extends ConstraintValidator<?, ?>>> validatedBy) {
		this.validatedBy = validatedBy;
	}

	public Annotation getInstance() {
		return instance;
	}

	public void setInstance(Annotation instance) {
		this.instance = instance;
	}

	public Set<Scope> getScope() {
		return scope;
	}

	public void setScope(Set<Scope> scope) {
		this.scope = scope;
	}

	public Set<ElementType> getElementTypes() {
		return elementTypes;
	}

	public void setElementTypes(Set<ElementType> elementTypes) {
		this.elementTypes = elementTypes;
	}

	public Set<ConstraintMetadata> getComposedOf() {
		return composedOf;
	}

	public void setComposedOf(Set<ConstraintMetadata> composedOf) {
		this.composedOf = composedOf;
	}

	public boolean isReportAsSingleViolation() {
		return reportAsSingleViolation;
	}

	public void setReportAsSingleViolation(boolean reportAsSingleViolation) {
		this.reportAsSingleViolation = reportAsSingleViolation;
	}

	public Set<ElementType> getDeclaredOn() {
		return declaredOn;
	}

	public void setDeclaredOn(Set<ElementType> declaredOn) {
		this.declaredOn = declaredOn;
	}

	public Map<String,OverridesMetadata> getOverridesMap() {
		return overridesMap;
	}

	public void setOverridesMap(Map<String,OverridesMetadata> overridesMap) {
		this.overridesMap = overridesMap;
	}	
	
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	public String toString() {
		String key = this.getInstance().toString();
		if(this.targetClass != null) {
			key += "@" + this.targetClass.getName();
		}
		return key;
	}
	
	//simple equals method based on key signature
	public boolean equals(Object object) {
		if(object == null) return false;
		if(object instanceof ConstraintMetadata) {
			return this.toString().equals(object.toString());
		}		
		return false;
	}
	
}

