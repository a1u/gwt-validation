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

//generic imports
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;
import com.em.validation.client.metadata.factory.IConcreteAnnotationInstanceFactory;

<#if targetAnnotation == "Pattern">
import javax.validation.constraints.Pattern.Flag;
</#if>

//passed in imports
<#list imports as import>
import ${import};
</#list>

//the target constraint annotation
import ${annotationImportName};

public enum ${generatedName} implements IConcreteAnnotationInstanceFactory<${targetAnnotation}> {

	INSTANCE;

	//the hashtable cache of lazily created annotation instances
	private Map<String,${targetAnnotation}> signatureToInstanceMap = new HashMap<String,${targetAnnotation}>();
	
	//the map from the hash signatures to the values
	private Map<String,Map<String,Object>> signatureToValueMap = new HashMap<String,Map<String,Object>>();
	
	private ${generatedName}() {
		//save properties for each signature
		<#list constraints as constraint>
		//metadata map for ${constraint.toString()}
		this.signatureToValueMap.put("${hash(constraint.toString())}",new HashMap<String,Object>());
		<#list constraint.methodMap?values as property>
		<#if property.returnType == "String">
		this.signatureToValueMap.get("${hash(constraint.toString())}").put("${property.methodName}","${prep(property.returnValue)?j_string}");
		<#else>
		this.signatureToValueMap.get("${hash(constraint.toString())}").put("${property.methodName}",${property.returnValue});
		</#if>
		</#list>
		
		</#list>
	}
	
	public ${targetAnnotation} getAnnotation(String signature) {
		//get from cache, if null, then instantiate
		${targetAnnotation} annotation = signatureToInstanceMap.get(signature);
		if(annotation == null) {
			//get value map
			final Map valueMap = this.signatureToValueMap.get(signature);
			//generate annotation and return
			annotation = new ${targetAnnotation}() {
				//generated body methods
				<#list methods as metadata>
				<#if metadata.returnType == "Class[]">@SuppressWarnings({ "rawtypes", "unchecked" })</#if>
				@Override
				public ${metadata.returnType} ${metadata.methodName}() {
					<#if metadata.returnType == "int">
					return ((Number)valueMap.get("${metadata.methodName}")).intValue();
					<#elseif metadata.returnType == "long">
					return ((Number)valueMap.get("${metadata.methodName}")).longValue();
					<#elseif metadata.returnType == "double">
					return ((Number)valueMap.get("${metadata.methodName}")).doubleValue();
					<#elseif metadata.returnType == "float">
					return ((Number)valueMap.get("${metadata.methodName}")).floatValue();
					<#elseif metadata.returnType == "boolean">
					return ((Boolean)valueMap.get("${metadata.methodName}")).booleanValue();
					<#elseif metadata.returnType == "char">
					return ((Character)valueMap.get("${metadata.methodName}")).charValue();
					<#elseif metadata.returnType == "short">
					return ((Number)valueMap.get("${metadata.methodName}")).shortValue();		
					<#else>
					return (${metadata.returnType})valueMap.get("${metadata.methodName}");
					</#if>
				}			
				</#list>
				
				@Override
				public Class<? extends Annotation> annotationType() {
					return ${targetAnnotation}.class;
				}
			};
			
			//store in cache for next try
			this.signatureToInstanceMap.put(signature,annotation);
		}
		return annotation;
	}
	
	public Map<String,Object> getPropertyMap(String signature) {
		return this.signatureToValueMap.get(signature);
	}
}