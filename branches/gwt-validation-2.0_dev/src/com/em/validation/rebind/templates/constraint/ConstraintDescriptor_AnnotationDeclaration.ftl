new ${targetAnnotation}() {
			//generated body methods
			<#list annotationMetadata as metadata>
			<#if metadata.returnType == "Class[]">@SuppressWarnings({ "rawtypes", "unchecked" })</#if>
			@Override
			public ${metadata.returnType} ${metadata.methodName}() {
				return ${metadata.returnValue};
			}			
			</#list>
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return ${targetAnnotation}.class;
			}		
		};