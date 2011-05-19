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