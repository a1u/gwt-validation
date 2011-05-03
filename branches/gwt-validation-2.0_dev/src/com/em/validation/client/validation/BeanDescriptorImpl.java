package com.em.validation.client.validation;

import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.validation.factory.DescriptorFactory;
import com.google.gwt.dev.util.collect.HashSet;

public class BeanDescriptorImpl extends ElementDescriptorImpl implements BeanDescriptor {

	@Override
	public Set<PropertyDescriptor> getConstrainedProperties() {
		Set<PropertyDescriptor> propertyDescriptors = new HashSet<PropertyDescriptor>();
		for(String propertyName : this.backingReflector.getPropertyNames()) {
			PropertyDescriptor descriptor = DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, propertyName);
			if(descriptor.hasConstraints()) {
				propertyDescriptors.add(descriptor);
			}
		}		
		return propertyDescriptors;
	}

	@Override
	public PropertyDescriptor getConstraintsForProperty(String name) {
		return DescriptorFactory.INSTANCE.getPropertyDescriptor(this.backingReflector, name);
	}

	@Override
	public boolean isBeanConstrained() {
		return false;
	}

}
