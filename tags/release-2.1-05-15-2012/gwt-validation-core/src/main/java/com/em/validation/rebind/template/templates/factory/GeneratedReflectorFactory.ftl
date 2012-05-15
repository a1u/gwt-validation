package ${targetPackage};

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

import com.em.validation.client.reflector.AbstractCompiledReflectorFactory;
import com.em.validation.client.reflector.IReflector;

import java.util.HashMap;
import java.util.Map;

public class ${className} extends AbstractCompiledReflectorFactory {

	public ${className}() {
		<#list reflectorMetadata as reflector>
		//create ${reflector.reflectorClass} for ${reflector.targetClass}
		this.reflectorCache.put(${reflector.targetClass}.class,new ${reflector.reflectorClass}());
		</#list>
		
		//now add all of the super reflectors
		<#list reflectorMetadata as reflector>
		<#if reflector.superClass != "">
		((${reflector.reflectorClass})this.reflectorCache.get(${reflector.targetClass}.class)).setSuperReflector((IReflector)this.getReflector(${reflector.superClass}.class));
		</#if>
		<#list reflector.reflectorInterfaces as iface>
		((${reflector.reflectorClass})this.reflectorCache.get(${reflector.targetClass}.class)).addReflectorInterface((IReflector)this.getReflector(${iface}.class));
		</#list>
		</#list>
		
		//=============================
		//uncovered interfaces
		//=============================
		<#list uncoveredMap?keys as className>
		//${className}'s interfaces 
		<#list uncoveredMap[className] as interfaceName>
		this.addInterfaceToClass("${className}",${interfaceName}.class);
		</#list>
		
		</#list>
	}

}