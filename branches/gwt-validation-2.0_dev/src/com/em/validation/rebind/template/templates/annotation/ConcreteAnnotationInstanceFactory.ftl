package ${targetPackage};

//generic imports
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;
import com.em.validation.client.metadata.factory.IConcreteAnnotationInstanceFactory;

<#if targetAnnotation == "Pattern">
import javax.validation.constraints.Pattern.Flag;
</#if>

//the target constraint annotation
import ${annotationImportName};

public enum ${generatedName} implements IConcreteAnnotationInstanceFactory<${targetAnnotation}> {

	INSTANCE;

	//the hashtable cache of lazily created annotation instances
	private Map<String,${targetAnnotation}> signatureToInstanceMap = new HashMap<String,${targetAnnotation}>();
	
	//the map from the hash signatures to the values
	private Map<String,Map<String,Object>> signatureToValueMap = new HashMap<String,Map<String,Object>>();
	
	private ${generatedName}() {
		//save properties for each signature
		<#list constraints as constraint>
		//metadata map for ${constraint.instance.toString()}
		this.signatureToValueMap.put("${constraint.instance.toString()}",new HashMap<String,Object>());
		<#list constraint.methodMap?values as property>
		this.signatureToValueMap.get("${constraint.instance.toString()}").put("${property.methodName}",${property.returnValue});
		</#list>
		
		</#list>
	}
	
	public ${targetAnnotation} getAnnotation(String signature) {
		//get from cache, if null, then instantiate
		${targetAnnotation} annotation = signatureToInstanceMap.get(signature);
		if(annotation == null) {
			//get value map
			final Map valueMap = this.signatureToValueMap.get(signature);
			//generate annotation and return
			annotation = new ${targetAnnotation}() {
				//generated body methods
				<#list methods as metadata>
				<#if metadata.returnType == "Class[]">@SuppressWarnings({ "rawtypes", "unchecked" })</#if>
				@Override
				public ${metadata.returnType} ${metadata.methodName}() {
					<#if metadata.returnType == "int">
					return ((Integer)valueMap.get("${metadata.methodName}")).intValue();
					<#elseif metadata.returnType == "long">
					return ((Long)valueMap.get("${metadata.methodName}")).longValue();
					<#else>
					return (${metadata.returnType})valueMap.get("${metadata.methodName}");
					</#if>
				}			
				</#list>
				
				@Override
				public Class<? extends Annotation> annotationType() {
					return ${targetAnnotation}.class;
				}
			};
			
			//store in cache for next try
			this.signatureToInstanceMap.put(signature,annotation);
		}
		return annotation;
	}
	
	public Map<String,Object> getPropertyMap(String signature) {
		return this.signatureToValueMap.get(signature);
	}
}