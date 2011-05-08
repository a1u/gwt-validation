package com.em.validation.client.metadata;

import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.reflector.IReflector;

public class PropertyDescriptorImpl extends ProtoDescriptor implements PropertyDescriptor {

	protected String propertyName = "";
	
	public PropertyDescriptorImpl(IReflector<?> reflector, String name) {
		super(reflector);
		this.propertyName = name;
	}

	@Override
	public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {
		return this.backingReflector.getConstraintDescriptors(this.propertyName);
	}

	@Override
	public Class<?> getElementClass() {
		return this.backingReflector.getPropertyType(this.propertyName);
	}

	@Override
	public boolean hasConstraints() {
		return this.getConstraintDescriptors().size() > 0;
	}

	@Override
	public String getPropertyName() {
		return this.propertyName;
	}

	@Override
	public boolean isCascaded() {
		return false;
	}

}
