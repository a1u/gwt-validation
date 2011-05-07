package com.em.validation.rebind.metadata;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassDescriptor {

	private Set<ClassDescriptor> dependencies = new LinkedHashSet<ClassDescriptor>();
	
	private String className = "";
	
	private String fullClassName = "";
	
	private String classContents = "";
	
	private String packageName = "";

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

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

	public Set<ClassDescriptor> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<ClassDescriptor> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((fullClassName == null) ? 0 : fullClassName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassDescriptor other = (ClassDescriptor) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (fullClassName == null) {
			if (other.fullClassName != null)
				return false;
		} else if (!fullClassName.equals(other.fullClassName))
			return false;
		return true;
	}	
}
