package com.em.validation.client.metadata.factory;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface IConcreteAnnotationInstanceFactory<T extends Annotation> {

	public T getAnnotation(String signature);
	
	public Map<String,Object> getPropertyMap(String signature);
	
}
