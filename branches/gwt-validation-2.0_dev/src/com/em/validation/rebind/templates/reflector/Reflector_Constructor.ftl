		//set the available property names in the constructor
		
		<#list properties as property>
		//${property.name}
		this.properties.add("${property.name}");
		<#if property.annotations?size &gt; 0>
		Set<Annotation> ${property.name}AnnotationList = new LinkedHashSet<Annotation>();
		<#list property.annotations as annotation>
		${property.name}AnnotationList.add(${annotation}.getAnnotationInstance());			
		</#list>
		this.annotationMap.put("${property.name}",${property.name}AnnotationList);
		</#if>

		</#list>