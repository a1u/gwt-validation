package com.em.validation.rebind.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public enum AnnotationProxyFactory {

	INSTANCE;
	
	private AnnotationProxyFactory() {
		
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getProxy(T annotation, Map<String, Object> overrides) {
		//create invocation handler for target class
		InvocationHandler handler = new AnnotationInvocationHandler(annotation,overrides);
		
		//get proxy class instance
		T instance = (T)Proxy.newProxyInstance(annotation.getClass().getClassLoader(), new Class<?>[]{annotation.annotationType()}, handler);
				
		return instance;
	}
	
}
