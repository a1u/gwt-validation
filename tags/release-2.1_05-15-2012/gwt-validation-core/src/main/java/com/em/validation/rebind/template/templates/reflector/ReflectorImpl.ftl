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

import java.util.Set;
import com.em.validation.client.reflector.AbstractCompiledReflector;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javax.validation.metadata.ConstraintDescriptor;

//super reflector and reflector interfaces
import com.em.validation.client.reflector.IReflector;

//decorate constraint descriptors so they show up as separate instances
import com.em.validation.client.metadata.ConstraintDescriptorDecorator;

//exceptions during validation
import javax.validation.ValidationException;

//we need the full directory where all the generated constraints are stored
import ${generatedConstraintPackage}.*;

<#list importList as import>
import ${import};
</#list>

public class ${concreteClassName} extends AbstractCompiledReflector {
	
	public ${concreteClassName}() {
		//set the target class
		this.targetClass = ${reflectionTargetName}.class;
	
		//set the available property names in the constructor
		<#list properties as property>
		//${property.name}
		this.properties.add("${property.name}");
		this.propertyTypes.put("${property.name}",${property.classString});
		this.declaredOnMethod.put("${property.name}",new LinkedHashSet<ConstraintDescriptor<?>>());
		this.declaredOnField.put("${property.name}",new LinkedHashSet<ConstraintDescriptor<?>>());
		<#if property.constraintDescriptorClasses?size &gt; 0>
		Set<ConstraintDescriptor<?>> ${property.name}ConstraintDescriptorList = new LinkedHashSet<ConstraintDescriptor<?>>();
		<#list property.constraintDescriptorClasses as constraintDescriptor>
		if(true) {
			ConstraintDescriptor<?> decorated = new ConstraintDescriptorDecorator(${constraintDescriptor}.INSTANCE);
			${property.name}ConstraintDescriptorList.add(decorated);	
		<#if declaredOnField?seq_contains(constraintDescriptor)>
			this.declaredOnField.get("${property.name}").add(decorated);					
		<#elseif declaredOnMethod?seq_contains(constraintDescriptor)>
			this.declaredOnMethod.get("${property.name}").add(decorated);
		</#if>
		}
		</#list>
		this.constraintDescriptors.put("${property.name}",${property.name}ConstraintDescriptorList);
		
		</#if>
		</#list>
		
		//class level validations
		<#if classLevelConstraints?size &gt; 0>
		Set<ConstraintDescriptor<?>> classLevelConstraints = new LinkedHashSet<ConstraintDescriptor<?>>();
		<#list classLevelConstraints as clConstraint>
		classLevelConstraints.add(new ConstraintDescriptorDecorator(${clConstraint}.INSTANCE));
		</#list>
		this.constraintDescriptors.put(this.targetClass.getName(),classLevelConstraints);
		</#if>
		
		//put the cascaded properties in the cascade
		<#list cascades as cascade>
		this.cascadedProperties.add("${cascade}");
		</#list>
		
		//group sequence list
		this.groupSequence = new Class<?>[${groupSequence?size}];
		<#if groupSequence?size &gt; 0>
		int groupIndex = 0;
		<#list groupSequence as group>
		this.groupSequence[groupIndex++] = ${group};
		</#list>
		</#if>
	}

	public Object getValue(String name, Object target){
		Object value = null;
	
		try {
			<#list properties as property><#if property_index &gt; 0> else </#if>if("${property.name}".equals(name)) {
				value = ((${reflectionTargetName})target).${property.accessor};				
			}</#list>
		} catch (Exception e) {
			throw new ValidationException("An error occurred while reflectively accessing a property.",e);
		}
		
		if(value == null) {
			value = this.getSuperValues(name, target);
		}
		
		return value;
	}
}