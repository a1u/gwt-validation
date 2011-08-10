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

//we need the full directory where all the generated constraints are stored
import ${generatedConstraintPackage}.*;

<#list importList as import>
import ${import};
</#list>

public class ${concreteClassName} extends AbstractCompiledReflector<${reflectionTargetName}> {
	
	public ${concreteClassName}() {
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
		
		//set the target class
		this.targetClass = ${reflectionTargetName}.class;
	}

	public Object getValue(String name, ${reflectionTargetName} target){
		Object value = null;
	
		<#list properties as property><#if property_index &gt; 0> else </#if>if("${property.name}".equals(name)) {
			value = target.${property.accessor};				
		}</#list>
		
		if(value == null) {
			value = this.getSuperValues(name, target);
		}
		
		return value;
	}
}