package com.em.validation.client.metadata.factory;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

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


import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Describes the available actions on a concrete annotation factory.  These are used only on the client and implemented
 * through the deferred binding mechanism.  This is how the generated code gets the annotation instances that are basically
 * just property maps wrapped in a fancy way.  It works almost exactly like the proxy mechanism does in the compiler.  Instead
 * of the defaults/declared properties on the annotation backing the proxy there is a hashmap.  
 * 
 * @author chris
 *
 * @param <T>
 */
public interface IConcreteAnnotationInstanceFactory<T extends Annotation> {

	/**
	 * Get the annotation based on the given signature.  This is actually a HASH of the toString() method on the proxy.
	 * 
	 * @param signature
	 * @return
	 */
	public T getAnnotation(String signature);
	
	/**
	 * Get the annotation value map that would be backing the "proxy" annotation instance
	 * 
	 * @param signature
	 * @return
	 */
	public Map<String,Object> getPropertyMap(String signature);
	
}
