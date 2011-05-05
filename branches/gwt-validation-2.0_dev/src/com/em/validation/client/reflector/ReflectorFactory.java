package com.em.validation.client.reflector;

import com.em.validation.rebind.reflector.factory.RuntimeReflectorFactory;

public enum ReflectorFactory implements IReflectorFactory {
	
	INSTANCE;
	
	private ReflectorFactory(){
	
	}

	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		//at runtime, delegate to a class that uses introspection and reflection
		//to create (and cache) reflectors
		return RuntimeReflectorFactory.INSTANCE.getReflector(targetClass);
	}

}
