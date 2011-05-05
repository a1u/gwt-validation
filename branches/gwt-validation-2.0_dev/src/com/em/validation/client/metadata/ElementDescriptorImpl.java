package com.em.validation.client.metadata;

import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;

import com.em.validation.client.reflector.IReflector;

public abstract class ElementDescriptorImpl extends ProtoDescriptor implements ElementDescriptor {

	protected IReflector<?> backingReflector = null;

	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		return backingReflector.getConstraintDescriptors();
	}

	@Override
	public Class<?> getElementClass() {
		return this.backingReflector.getTargetClass();
	}

	@Override
	public boolean hasConstraints() {
		return this.getConstraintDescriptors().size() > 0;
	}
	
}
