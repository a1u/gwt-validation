		//set the available property names in the constructor
		
		<#list properties as property>
		//${property.name}
		this.properties.add("${property.name}");
		<#if property.annotations?size &gt; 0>
		Set<ConstraintDescriptor<?>> ${property.name}AnnotationList = new LinkedHashSet<ConstraintDescriptor<?>>();
		<#list property.annotations as annotation>
		${property.name}AnnotationList.add(new ${annotation}());			
		</#list>
		this.constraintDescriptors.put("${property.name}",${property.name}AnnotationList);
		</#if>

		</#list>