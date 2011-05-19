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

//we need the full directory where all the generated constraints are stored
import ${generatedConstraintPackage}.*;

<#list importList as import>
import ${import};
</#list>

public class ${concreteClassName} extends Reflector<${reflectionTargetName}> {
	public ${concreteClassName}() {
		//set the available property names in the constructor
		
		<#list properties as property>
		//${property.name}
		this.properties.add("${property.name}");
		this.propertyTypes.put("${property.name}",${property.classString});
		<#if property.annotations?size &gt; 0>
		Set<ConstraintDescriptor<?>> ${property.name}ConstraintDescriptorList = new LinkedHashSet<ConstraintDescriptor<?>>();
		<#list property.annotations as annotation>
		${property.name}ConstraintDescriptorList.add(${annotation}.INSTANCE);			
		</#list>
		this.constraintDescriptors.put("${property.name}",${property.name}ConstraintDescriptorList);
		</#if>
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
}