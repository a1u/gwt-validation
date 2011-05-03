package com.em.validation.client.validation.metadata;

import javax.validation.metadata.ElementDescriptor;

public abstract class ProtoDescriptor implements ElementDescriptor{

	@Override
	public ConstraintFinder findConstraints() {
		return null;
	}
	
}
