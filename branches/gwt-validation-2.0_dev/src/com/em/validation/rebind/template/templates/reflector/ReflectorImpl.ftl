package ${targetPackage};

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
	
		return value;
	}
}