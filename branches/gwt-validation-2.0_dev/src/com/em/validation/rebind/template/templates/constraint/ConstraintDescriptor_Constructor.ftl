		//create underlying annotation
		this.annotation = <#include "ConstraintDescriptor_AnnotationDeclaration.ftl">;

		//save properties
		<#list annotationMetadata as metadata>
		this.propertyMap.put("${metadata.methodName}",this.annotation.${metadata.methodName}());
		</#list>
		
		//save composed constraints
		<#list composedOf as composed>
		this.composedOf.add(new ${composed.className}());
		</#list>
		
		//report as single violation
		this.reportAsSingleViolation = ${reportAsSingleViolation};