package com.em.validation.rebind.reflector;

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
