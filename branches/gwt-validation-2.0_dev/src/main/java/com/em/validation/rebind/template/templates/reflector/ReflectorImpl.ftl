package ${targetPackage};

/* 
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
import com.em.validation.client.reflector.Reflector;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
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

public class ${concreteClassName} extends Reflector<${reflectionTargetName}> {
	
	private Set<String> cascadedProperties = new LinkedHashSet<String>();

	public ${concreteClassName}() {
		//set the available property names in the constructor
		
		<#list properties as property>
		//${property.name}
		this.properties.add("${property.name}");
		this.propertyTypes.put("${property.name}",${property.classString});
		<#if property.constraintDescriptorClasses?size &gt; 0>
		Set<ConstraintDescriptor<?>> ${property.name}ConstraintDescriptorList = new LinkedHashSet<ConstraintDescriptor<?>>();
		<#list property.constraintDescriptorClasses as constraintDescriptor>
		${property.name}ConstraintDescriptorList.add(new ConstraintDescriptorDecorator(${constraintDescriptor}.INSTANCE));			
		</#list>
		this.constraintDescriptors.put("${property.name}",${property.name}ConstraintDescriptorList);
		</#if>
		</#list>
		
		//put the cascaded properties in the cascade
		<#list cascades as cascade>
		this.cascadedProperties.add("${cascade}");
		</#list>
		
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
	
	@Override
	public boolean isCascaded(String propertyName) {
		boolean result = false;
		
		//check the cascaded properties set first
		result = this.cascadedProperties.contains(propertyName);
		
		//if still false after checking property and field, continue to check other values
		if(result == false) {
			if(this.superReflector != null) {
				result = this.superReflector.isCascaded(propertyName);
			}
			if(result == false) {
				for(IReflector<?> iface : this.reflectorInterfaces) {
					result = iface.isCascaded(propertyName);
					if(result) break;
				}
			}			
		}
		
		return result;
	}
	
		
	/**
	 * Get the return type of a given property name
	 */
	public Class<?> getPropertyType(String name) {
		Class<?> result = this.propertyTypes.get(name);
		
		if(result == null) {
			if(this.superReflector != null) {
				result = this.superReflector.getPropertyType(name);
			}
			if(result == null) {
				for(IReflector<?> iface : this.reflectorInterfaces) {
					result = iface.getPropertyType(name);
					if(result != null) break;
				}
			}	
		}
		
		return result;
	}
}