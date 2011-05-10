package com.em.validation.rebind.reflector.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.rebind.reflector.RuntimeReflectorImpl;
import com.em.validation.rebind.resolve.ConstraintResolver;

public enum RuntimeReflectorFactory implements IReflectorFactory {
	
	INSTANCE;
	
	private Map<Class<?>, IReflector<?>> reflectorCache = new HashMap<Class<?>, IReflector<?>>();
	
	private RuntimeReflectorFactory() {
		
	}

	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		@SuppressWarnings("unchecked")
		IReflector<T> reflector = (IReflector<T>)this.reflectorCache.get(targetClass);				
		if(reflector == null) {
			reflector = new RuntimeReflectorImpl<T>(targetClass);
			
			for(Set<ConstraintDescriptor<?>> subset : ConstraintResolver.INSTANCE.getConstraintDescriptors(targetClass).values()){
				reflector.getConstraintDescriptors().addAll(subset);
			}
			this.reflectorCache.put(targetClass, reflector);
		}
		
		return reflector;
	}

}
