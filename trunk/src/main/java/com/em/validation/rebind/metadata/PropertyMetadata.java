package com.em.validation.rebind.metadata;

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
