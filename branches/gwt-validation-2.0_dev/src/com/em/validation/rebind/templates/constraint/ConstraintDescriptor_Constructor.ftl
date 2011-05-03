		//create underlying annotation
		this.annotation = <#include "ConstraintDescriptor_AnnotationDeclaration.ftl">;

		//save properties
		<#list annotationMetadata as metadata>
		this.propertyMap.put("${metadata.methodName}",this.annotation.${metadata.methodName}());
		</#list>