package com.em.validation.rebind.metadata;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class PropertyMetadata {


	private List<String> annotations = new ArrayList<String>();
	private List<Annotation> annotationInstances = new ArrayList<Annotation>();
	
	private String name;
	private String accessor;
	private String classString;
	
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

	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
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
	
	
}
