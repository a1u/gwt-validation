package ${targetPackage};

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

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
		//metadata map for ${constraint.instance.toString()}
		this.signatureToValueMap.put("${hash(constraint.instance.toString())}",new HashMap<String,Object>());
		<#list constraint.methodMap?values as property>
		<#if property.returnType == "String">
		this.signatureToValueMap.get("${hash(constraint.instance.toString())}").put("${property.methodName}","${prep(property.returnValue)?j_string}");
		<#else>
		this.signatureToValueMap.get("${hash(constraint.instance.toString())}").put("${property.methodName}",${property.returnValue});
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
					return (int)(Integer)valueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "long">
					return (long)(Long)valueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "double">
					return (double)(Double)valueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "float">
					return (float)(Float)valueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "boolean">
					return (boolean)(Boolean)vaueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "char">
					return (char)(Char)valueMap.get("${metadata.methodName}");
					<#elseif metadata.returnType == "short">
					return (short)(Short)valueMap.get("${metadata.methodName}");
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