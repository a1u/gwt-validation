package com.em.validation.client.metadata;

import javax.validation.metadata.ElementDescriptor;

public abstract class ProtoDescriptor implements ElementDescriptor{

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
