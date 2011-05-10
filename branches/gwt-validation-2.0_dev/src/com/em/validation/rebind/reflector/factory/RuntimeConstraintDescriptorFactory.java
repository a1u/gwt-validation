package com.em.validation.rebind.reflector.factory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.RuntimeConstraintDescriptor;

public enum RuntimeConstraintDescriptorFactory {
	
	INSTANCE;

	private Map<String, ConstraintDescriptor<?>> descriptorCache = new HashMap<String, ConstraintDescriptor<?>>();
	
	private RuntimeConstraintDescriptorFactory() {
		
	}
	
	public ConstraintDescriptor<?> getConstraintDescriptor(ConstraintMetadata metadata) {
		ConstraintDescriptor<?> descriptor = this.descriptorCache.get(metadata.getInstance().toString());
		if(descriptor == null) {
			descriptor = new RuntimeConstraintDescriptor<Annotation>(metadata);
			this.descriptorCache.put(metadata.getInstance().toString(), descriptor);
			for(ConstraintMetadata sub : metadata.getComposedOf()) {
				descriptor.getComposingConstraints().add(this.getConstraintDescriptor(sub));
			}
		}
		return descriptor;
	}

}
