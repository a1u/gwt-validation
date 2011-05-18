package com.em.validation.rebind.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationInvocationHandler implements InvocationHandler {

	private Map<String,Object> override = new HashMap<String, Object>();
	private Annotation annotation = null;
	
	public <T extends Annotation> AnnotationInvocationHandler(T annotation,Map<String, Object> override) {
		this.annotation = annotation;
		this.override = override;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//check override map for object first
		Object result = this.override.get(method.getName());

		//then check method invocation
		if(result == null) {
			result = method.invoke(this.annotation, args);
		}
		
		//finally invoke on object, should return default
		if(result == null) {
			result = method.invoke(proxy, args);
		}
		
		//return result
		return result;
	}

}
