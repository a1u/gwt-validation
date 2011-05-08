package com.em.validation.client.reflector;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.Set;

public enum TypeResolver implements ITypeResolver {
	
	INSTANCE;
	
	private TypeResolver() {
		
	}

	@Override
	public Set<ElementType> getElementType(Annotation annotation) {

		return null;
	}
}
