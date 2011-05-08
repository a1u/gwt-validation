package com.em.validation.client.metadata;

import javax.validation.metadata.ElementDescriptor;

import com.em.validation.client.reflector.IReflector;

public abstract class ProtoDescriptor implements ElementDescriptor{
	
	protected IReflector<?> backingReflector = null;
	
	public ProtoDescriptor(IReflector<?> reflector) {
		this.backingReflector = reflector;
	}
	
	@Override
	public ConstraintFinder findConstraints() {
		final class PrivateConstraintFinderImpl extends ConstraintFinderImpl {
			public PrivateConstraintFinderImpl(ElementDescriptor descriptor) {
				super(descriptor);
			}			
		}
		
		return new PrivateConstraintFinderImpl(this);
	}
	
}
