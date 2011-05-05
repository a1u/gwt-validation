package com.em.validation.rebind.reflector.factory;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public enum RuntimeReflectorFactory implements IReflectorFactory {
	
	INSTANCE;
	
	private RuntimeReflectorFactory() {
		
	}

	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		return null;
	}

}
