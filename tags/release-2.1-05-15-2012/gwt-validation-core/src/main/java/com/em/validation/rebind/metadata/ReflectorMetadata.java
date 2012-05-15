package com.em.validation.rebind.metadata;

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

import java.util.HashSet;
import java.util.Set;

public class ReflectorMetadata {

	private String reflectorClass = "";
	private String targetClass = "";
	private String superClass = "";
	private Set<String> reflectorInterfaces = new HashSet<String>();
	
	public String getReflectorClass() {
		return reflectorClass;
	}
	public void setReflectorClass(String reflectorClass) {
		this.reflectorClass = reflectorClass;
	}
	
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	
	public String getSuperClass() {
		return superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	
	public Set<String> getReflectorInterfaces() {
		return reflectorInterfaces;
	}
	public void setReflectorInterfaces(Set<String> reflectorInterfaces) {
		this.reflectorInterfaces = reflectorInterfaces;
	}
	
}
