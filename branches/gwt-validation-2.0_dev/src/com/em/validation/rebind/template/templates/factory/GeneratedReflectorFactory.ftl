package ${targetPackage};

import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.IReflector;

import java.util.HashMap;
import java.util.Map;

public class ${className} implements IReflectorFactory {

	private Map<Class<?>,IReflector<?>> reflectorCache = new HashMap<Class<?>,IReflector<?>>();

	public ${className}() {
		<#list reflectorMetadata as reflector>
			reflectorCache.put(${reflector.targetClass}.class,new ${reflector.reflectorClass}());
		</#list>
	}
	
	@Override
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass) {
		return (IReflector<T>)this.reflectorCache.get(targetClass);
	}

}