package com.em.validation.rebind.reflector;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnnotationInvocationHandler implements InvocationHandler {

	private Map<String,Object> override = new HashMap<String, Object>();
	private Annotation annotation = null;
	
	private String id = "";
	
	public <T extends Annotation> AnnotationInvocationHandler(T annotation,Map<String, Object> override) {
		this.annotation = annotation;
		this.override = override;
		
		//create id, this will be part of the "toString" signature
		this.id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""); 
		
		StringBuilder signature = new StringBuilder();
		signature.append("@");
		signature.append(annotation.annotationType().getName()); 
		signature.append("(proxyid=");
		signature.append(this.id);
		
		if(this.override.size() > 0) {
			signature.append(", overrides=[");
			int index = 0;
			for(String key : this.override.keySet()) {
				if(index++ > 0) {
					signature.append(", ");
				}
				signature.append(key);
			}			
			signature.append("]");
		} 
		signature.append(")");	
		
		//add id to map
		this.override.put("toString", signature.toString());
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//check override map for object first
		Object result = this.override.get(method.getName());

		//then check method invocation
		if(result == null) {
			result = method.invoke(this.annotation, args);
		}
		
		//return result
		return result;
	}

}
