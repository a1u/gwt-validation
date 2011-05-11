package com.em.validation.rebind.reflector.factory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.Reflector;
import com.em.validation.client.reflector.ReflectorFactory;
import com.em.validation.rebind.reflector.RuntimeReflectorImpl;
import com.em.validation.rebind.resolve.ConstraintDescriptionResolver;

public enum RuntimeReflectorFactory implements IReflectorFactory {
	
	INSTANCE;
	
	private Map<Class<?>, RuntimeReflectorImpl<?>> reflectorCache = new HashMap<Class<?>, RuntimeReflectorImpl<?>>();
	
	private RuntimeReflectorFactory() {
		
	}

	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		//return a null reflector when the object doesn't warrant reflecting
		if(Object.class.equals(targetClass) || Annotation.class.equals(targetClass) || targetClass == null) return null;
		
		@SuppressWarnings("unchecked")
		RuntimeReflectorImpl<T> reflector = (RuntimeReflectorImpl<T>)this.reflectorCache.get(targetClass);				
		if(reflector == null) {
			reflector = new RuntimeReflectorImpl<T>(targetClass);
			
			this.reflectorCache.put(targetClass, reflector);
			
			//add constraint descriptors
			reflector.setConstraintDescriptorMap(ConstraintDescriptionResolver.INSTANCE.getConstraintDescriptors(targetClass));
			
			RuntimeReflectorImpl<T> runtime = (RuntimeReflectorImpl<T>)reflector;
			runtime.setSuperReflector((Reflector<?>)ReflectorFactory.INSTANCE.getReflector(targetClass.getSuperclass()));
			for(Class<?> iface : targetClass.getInterfaces()) {
				runtime.addReflectorInterface((Reflector<?>)ReflectorFactory.INSTANCE.getReflector(iface));
			}			
		}
		
		return reflector;
	}

}
