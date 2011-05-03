		//create underlying annotation
		this.annotation = ${generatedName}.baseAnnotation;

		//save properties
		<#list annotationMetadata as metadata>
		this.propertyMap.put("${metadata.methodName}",this.annotation.${metadata.methodName}());
		</#list>