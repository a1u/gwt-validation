package ${targetPackage};

import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.IReflector;

import java.util.HashMap;
import java.util.Map;

public class ${className} implements IReflectorFactory {

	private Map<Class<?>,IReflector<?>> reflectorCache = new HashMap<Class<?>,IReflector<?>>();
	
	public ${className}() {
		<#list reflectorMetadata as reflector>
		//create ${reflector.reflectorClass} for ${reflector.targetClass}
		reflectorCache.put(${reflector.targetClass}.class,new ${reflector.reflectorClass}());
		</#list>
		
		//now add all of the super reflectors
		<#list reflectorMetadata as reflector>
		<#if reflector.superClass != "">
		((${reflector.reflectorClass})this.reflectorCache.get(${reflector.targetClass}.class)).setSuperReflector((IReflector<?>)this.getReflector(${reflector.superClass}.class));
		</#if>
		<#list reflector.reflectorInterfaces as iface>
		((${reflector.reflectorClass})this.reflectorCache.get(${reflector.targetClass}.class)).addReflectorInterface((IReflector<?>)this.getReflector(${iface}.class));
		</#list>
		</#list>
		
	}
	
	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		if(targetClass == null || Object.class.equals(targetClass)) return null;
		return (IReflector<T>)this.reflectorCache.get(targetClass);
	}

}