package com.em.validation.rebind.metadata;

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
	private Set<ConstraintMetadata> composedOf = new HashSet<ConstraintMetadata>();
	
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
		
}

