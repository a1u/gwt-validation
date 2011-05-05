package com.em.validation.client.reflector;

import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.rebind.reflector.factory.RuntimeReflectorFactory;
import com.google.gwt.core.client.GWT;

public enum ReflectorFactory implements IReflectorFactory {
	
	INSTANCE;
	
	private IReflectorFactory factory = null;
	
	private ReflectorFactory(){
		this.factory = (IReflectorFactory)GWT.create(IReflectorFactory.class);
	}

	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		return this.factory.getReflector(targetClass);
	}

}
