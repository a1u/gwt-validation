package com.em.validation.client.reflector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AbstractCompiledReflectorFactory implements IReflectorFactory {

	protected Map<Class<?>,IReflector> reflectorCache = new HashMap<Class<?>,IReflector>();
	
	protected Set<IReflector> suplementalInterfaceReflectors = new HashSet<IReflector>();
	
	@Override
	public IReflector getReflector(Class<?> targetClass) {
		if(targetClass == null || Object.class.equals(targetClass)) return null;
		IReflector reflector = this.reflectorCache.get(targetClass);
		
		//grab a pure delegating (empty, forwarding) reflector if reflector is still null
		if(reflector == null) {
			PureDelegatingReflector dReflector = new PureDelegatingReflector();
			
			//fill out superclass chain
			dReflector.targetClass = targetClass;
			dReflector.superReflector = this.getReflector(targetClass.getSuperclass());	
			
			//save back to cache so it doesn't have to be calculated again
			reflector = dReflector;
			this.reflectorCache.put(targetClass, reflector);
		}
		
		//resolve list of interface reflectors
		Set<Class<?>> currentInterfaces = new HashSet<Class<?>>();
		Set<IReflector> currentInterfaceReflectors = reflector.getInterfaceReflectors();
		for(IReflector iReflector : currentInterfaceReflectors) {
			if(iReflector.getTargetClass().isInterface()) {
				currentInterfaces.add(iReflector.getTargetClass());
			}
		}
		
		//now the list of current interfaces has all of the interfaces defined by it's parent reflectors.  we need to
		//update that list to include all of the parallel interfaces
		reflector.getInterfaceReflectors().addAll(this.suplementalInterfaceReflectors);
				
		return reflector;
	}
}
