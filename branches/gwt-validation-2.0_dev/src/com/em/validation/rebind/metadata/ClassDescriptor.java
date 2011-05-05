package com.em.validation.rebind.metadata;

import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor {

	private List<ClassDescriptor> dependencies = new ArrayList<ClassDescriptor>();
	
	private String className = "";
	
	private String fullClassName = "";
	
	private String classContents = "";

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getClassContents() {
		return classContents;
	}

	public void setClassContents(String classContents) {
		this.classContents = classContents;
	}

	public List<ClassDescriptor> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<ClassDescriptor> dependencies) {
		this.dependencies = dependencies;
	}
	
	
}
