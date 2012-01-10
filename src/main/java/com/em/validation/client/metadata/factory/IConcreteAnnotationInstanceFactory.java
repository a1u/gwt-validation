package com.em.validation.client.metadata.factory;

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
import java.util.Map;

/**
 * Describes the available actions on a concrete annotation factory.  These are used only on the client and implemented
 * through the deferred binding mechanism.  This is how the generated code gets the annotation instances that are basically
 * just property maps wrapped in a fancy way.  It works almost exactly like the proxy mechanism does in the compiler.  Instead
 * of the defaults/declared properties on the annotation backing the proxy there is a hashmap.  
 * 
 * @author chris
 *
 * @param <T>
 */
public interface IConcreteAnnotationInstanceFactory<T extends Annotation> {

	/**
	 * Get the annotation based on the given signature.  This is actually a HASH of the toString() method on the proxy.
	 * 
	 * @param signature
	 * @return
	 */
	public T getAnnotation(String signature);
	
	/**
	 * Get the annotation value map that would be backing the "proxy" annotation instance
	 * 
	 * @param signature
	 * @return
	 */
	public Map<String,Object> getPropertyMap(String signature);
	
}
