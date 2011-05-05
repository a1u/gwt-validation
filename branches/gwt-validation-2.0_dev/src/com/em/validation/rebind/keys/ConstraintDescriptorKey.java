package com.em.validation.rebind.keys;

import java.lang.annotation.Annotation;

public class ConstraintDescriptorKey {
	private Class<?> targetClass;
	private String propertyName;
	private Annotation annotation;
	
	public ConstraintDescriptorKey(Class<?> targetClass, String propertyName, Annotation annotation) {
		this.targetClass = targetClass;
		this.propertyName = propertyName;
		this.annotation = annotation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotation == null) ? 0 : annotation.hashCode());
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result
				+ ((targetClass == null) ? 0 : targetClass.hashCode());
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
		ConstraintDescriptorKey other = (ConstraintDescriptorKey) obj;
		if (annotation == null) {
			if (other.annotation != null)
				return false;
		} else if (!annotation.equals(other.annotation))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		return true;
	}
	
}
