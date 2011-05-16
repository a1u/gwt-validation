package com.em.validation.rebind.reflector.factory;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

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
