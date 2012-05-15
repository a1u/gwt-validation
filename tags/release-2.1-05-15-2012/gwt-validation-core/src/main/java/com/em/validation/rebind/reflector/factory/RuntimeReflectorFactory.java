package com.em.validation.rebind.reflector.factory;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
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
	
	private Map<Class<?>, RuntimeReflectorImpl> reflectorCache = new HashMap<Class<?>, RuntimeReflectorImpl>();
	
	private RuntimeReflectorFactory() {
		
	}

	@Override
	public IReflector getReflector(Class<?> targetClass) {
		//return a null reflector when the object doesn't warrant reflecting
		if(Object.class.equals(targetClass) || Annotation.class.equals(targetClass) || targetClass == null) return null;
		
		RuntimeReflectorImpl reflector = (RuntimeReflectorImpl)this.reflectorCache.get(targetClass);				
		if(reflector == null) {
			reflector = new RuntimeReflectorImpl(targetClass);
			
			this.reflectorCache.put(targetClass, reflector);
			
			//add constraint descriptors
			reflector.setConstraintDescriptorMap(ConstraintDescriptionResolver.INSTANCE.getConstraintDescriptors(targetClass));

			//add the super reflector and interface reflectors
			RuntimeReflectorImpl runtime = (RuntimeReflectorImpl)reflector;
			runtime.setSuperReflector((Reflector)ReflectorFactory.INSTANCE.getReflector(targetClass.getSuperclass()));
			for(Class<?> iface : targetClass.getInterfaces()) {
				runtime.addReflectorInterface((Reflector)ReflectorFactory.INSTANCE.getReflector(iface));
			}			
		}
		
		return reflector;
	}

}
