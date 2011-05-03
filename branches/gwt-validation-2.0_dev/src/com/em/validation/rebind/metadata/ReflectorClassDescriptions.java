package com.em.validation.rebind.metadata;

import java.util.ArrayList;
import java.util.List;

public class ReflectorClassDescriptions {
	private List<ClassDescriptor> constraintDescriptors = new ArrayList<ClassDescriptor>();
	
	private ClassDescriptor classDescriptor = null;

	public List<ClassDescriptor> getConstraintDescriptors() {
		return constraintDescriptors;
	}

	public void setConstraintDescriptors(List<ClassDescriptor> constraintDescriptors) {
		this.constraintDescriptors = constraintDescriptors;
	}

	public ClassDescriptor getClassDescriptor() {
		return classDescriptor;
	}

	public void setClassDescriptor(ClassDescriptor classDescriptor) {
		this.classDescriptor = classDescriptor;
	}
}
