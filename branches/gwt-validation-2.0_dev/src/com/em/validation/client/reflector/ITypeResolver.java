package com.em.validation.client.reflector;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.Set;

public interface ITypeResolver {

	public Set<ElementType> getElementType(Annotation annotation);
	
}
