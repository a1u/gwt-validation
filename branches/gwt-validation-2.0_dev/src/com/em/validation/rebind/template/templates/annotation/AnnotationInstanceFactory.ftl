package ${targetPackage};

//generic imports
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

//implementation import
import com.em.validation.client.metadata.factory.IConcreteAnnotationInstanceFactory;

public enum AnnotationInstanceFactory {

	INSTANCE;

	//the hashtable cache of lazily created annotation instances
	private Map<Class<?>,IConcreteAnnotationInstanceFactory<? extends Annotation>> factoryCache = new HashMap<Class<?>,IConcreteAnnotationInstanceFactory<? extends Annotation>>();
	
	private AnnotationInstanceFactory() {
		<#list factoryMap?keys as className>
		this.factoryCache.put(${className}.class,${factoryMap[className]}.INSTANCE);
		</#list>
	}
	
    @SuppressWarnings("unchecked")
	public <T extends Annotation> IConcreteAnnotationInstanceFactory<T> getAnnotationFactory(Class<T> targetAnnotationClass) {
            return (IConcreteAnnotationInstanceFactory<T>) this.factoryCache.get(targetAnnotationClass);
    }
	
}