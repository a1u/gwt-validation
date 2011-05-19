package ${targetPackage};

/* 
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

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