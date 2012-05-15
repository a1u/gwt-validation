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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnnotationInvocationHandler implements InvocationHandler {

	private Map<String,Object> override = new HashMap<String, Object>();
	private Annotation annotation = null;
	
	private String id = "";
	
	public <T extends Annotation> AnnotationInvocationHandler(T annotation,Map<String, Object> override) {
		this.annotation = annotation;
		this.override = override;
		
		//create id, this will be part of the "toString" signature
		this.id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""); 
		
		StringBuilder signature = new StringBuilder();
		signature.append("@");
		signature.append(annotation.annotationType().getName()); 
		signature.append("(proxyid=");
		signature.append(this.id);
		
		if(this.override.size() > 0) {
			signature.append(", overrides=[");
			int index = 0;
			for(String key : this.override.keySet()) {
				if(index++ > 0) {
					signature.append(", ");
				}
				signature.append(key);
			}			
			signature.append("]");
		} 
		signature.append(")");	
		
		//add id to map
		this.override.put("toString", signature.toString());
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//check override map for object first
		Object result = this.override.get(method.getName());

		//then check method invocation
		if(result == null) {
			result = method.invoke(this.annotation, args);
		}
		
		//return result
		return result;
	}

}
