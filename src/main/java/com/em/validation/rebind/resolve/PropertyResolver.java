package com.em.validation.rebind.resolve;

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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;

import com.em.validation.rebind.metadata.PropertyMetadata;

public enum PropertyResolver {

	INSTANCE;
	
	private Map<Class<?>, Map<String,PropertyMetadata>> metadataCache = new LinkedHashMap<Class<?>, Map<String,PropertyMetadata>>();
	
	private PropertyResolver() {
		
	}
	
	public Map<String,PropertyMetadata> getPropertyMetadata(Class<?> targetClass) {
		Map<String,PropertyMetadata> results = this.metadataCache.get(targetClass);
		if(results == null) {
			//resolve
			results = this.resolve(targetClass);
			//update cache
			this.metadataCache.put(targetClass, results);
		}
		return results;
	}
	
	public PropertyMetadata getPropertyMetadata(Class<?> targetClass, String propertyName) {
		return this.getPropertyMetadata(targetClass).get(propertyName);
	}
	
	private Map<String,PropertyMetadata> resolve(Class<?> targetClass) {
		//result map
		Map<String,PropertyMetadata> metadataMap = new LinkedHashMap<String, PropertyMetadata>();
		
		//set of initial property descriptors
		Set<PropertyDescriptor> propertyDescriptors = new LinkedHashSet<PropertyDescriptor>();
		//recursively resolve the property descriptors for the given target class
		propertyDescriptors.addAll(resolveAllPropertyDescriptors(targetClass).values());		
		
		//this will be used to filter against later in the process.  this will allow us to
		//know if a property has a publicly accessible field or if it has a method that should
		//be used instead.
		HashMap<String,Field> publicFields = new HashMap<String, Field>();
		for(Field field : targetClass.getDeclaredFields()) {
			publicFields.put(field.getName(),field);
		}
		
		//save the property and it's proper accessor to the property metadata list;
		for(PropertyDescriptor property : propertyDescriptors) {
		
			String propertyName = property.getName();
			
			//skip the "class" property.  we can't really validate that anyway.
			if("class".equals(propertyName)) continue;

			PropertyMetadata pMeta = new PropertyMetadata();
			pMeta.setName(propertyName);
			pMeta.setClassString(property.getPropertyType().getName() + ".class");
			pMeta.setReturnType(property.getPropertyType());
			
			if(this.hasMethod(targetClass, property.getReadMethod().getName(), new Class<?>[]{})) {
				pMeta.setAccessor(property.getReadMethod().getName() + "()");
				pMeta.setField(false);
			} else if(this.hasField(targetClass, propertyName)) {
				pMeta.setAccessor(propertyName);
				pMeta.setField(true);
			} else {
				continue;
			}
			
			//add the property to the metadata list
			metadataMap.put(propertyName,pMeta);
			
			//method and field annotations for composition mechanism
			Map<String,Annotation> potentialAnnotations = new LinkedHashMap<String, Annotation>();
			
			try {
				Field field = targetClass.getField(propertyName);
				for(Annotation a : field.getAnnotations()) {
					String id = field.getName() + ":" + a.annotationType().getName();
					potentialAnnotations.put(id, a);
				}
			} catch (Exception ex) {
				//log no field access
			}
			
			//recursively resolve all annotations for the method on the given class
			try {
				Method method = property.getReadMethod();
				String methodName = property.getReadMethod().getName();
				Class<?> infoClass = method.getDeclaringClass();
				for(Class<?> infoInterface : infoClass.getInterfaces()) {
					if(this.hasMethod(infoInterface, methodName, new Class<?>[]{})) {
						Method interfaceMethod = infoInterface.getDeclaredMethod(methodName, new Class<?>[]{});
						for(Annotation a : interfaceMethod.getAnnotations()) {
							String id = methodName + ":" + a.annotationType().getName();
							potentialAnnotations.put(id, a);
						}
					}
				}
					
				for(Annotation a : method.getAnnotations()) {
					String id = methodName + ":" + a.annotationType().getName();
					potentialAnnotations.put(id, a);
				}
			} catch (Exception ex) {
				//log no method access (?)
			}
			
			List<Annotation> annotations = this.getContstraintAnnotations(new ArrayList<Annotation>(potentialAnnotations.values()));
			pMeta.getAnnotationInstances().addAll(annotations);
		}
		
		//process remaining fields
		for(String fieldName : publicFields.keySet()) {

			//get field
			Field field = publicFields.get(fieldName);
			
			//add reflective bits
			PropertyMetadata pMeta = metadataMap.get(fieldName);
			if(pMeta == null) {
				//instantiate
				pMeta = new PropertyMetadata();
				//set properties
				pMeta.setName(fieldName);
				pMeta.setAccessor(fieldName);
				pMeta.setClassString(field.getType().getName() + ".class");
				//add to the metadata list
				metadataMap.put(fieldName,pMeta);			
			}

			//process annotations
			List<Annotation> annotations = this.getContstraintAnnotations(Arrays.asList(field.getAnnotations()));
			pMeta.getAnnotationInstances().addAll(annotations);
		}
		
		return metadataMap;
	}
	
	private boolean hasField(Class<?> targetClass, String targetFieldName) {
		if(targetClass == null) return false;
		try {
			if(targetClass.getDeclaredField(targetFieldName) != null) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		return false;
	}

	private boolean hasMethod(Class<?> targetClass, String targetFieldName, Class<?>[] parameterTypes) {
		if(targetClass == null) return false;
		try {
			if(targetClass.getDeclaredMethod(targetFieldName,parameterTypes) != null) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return false;
	}
	
	private Map<String, PropertyDescriptor> resolveAllPropertyDescriptors(Class<?> targetClass) {
		Map<String, PropertyDescriptor> descriptors = new LinkedHashMap<String, PropertyDescriptor>();
		
		//return the empty list if the target is null
		if(targetClass == null) return descriptors;
				
		BeanInfo targetInfo = null;
		try {
			targetInfo = Introspector.getBeanInfo(targetClass);
			for(PropertyDescriptor property : targetInfo.getPropertyDescriptors()) {
				descriptors.put(property.getName(), property);
			}
		} catch (IntrospectionException e) {
			//do nothing
		}
		
		return descriptors;
	}

	public List<Annotation> getContstraintAnnotations(List<Annotation> inputList) {
		
		//the list of approved/correct annotations
		List<Annotation> propertyAnnotations = new ArrayList<Annotation>();
		
		for(Annotation a : inputList) {
			//get the annotation type
			Class<? extends Annotation> annotationType = a.annotationType();
			
			//potential annotations that have a constraint defined continue to the next round of processing
			if(!annotationType.isAnnotationPresent(Constraint.class)) {
				Method valueMethod = null;
				try {
					valueMethod = annotationType.getDeclaredMethod("value", new Class<?>[]{});
				} catch (SecurityException e) {
				} catch (NoSuchMethodException e) {
				}
				if(valueMethod != null) {
					Object value = null;
					try {
						value = valueMethod.invoke(a, new Object[]{});
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
					if(value != null) {
						Class<?> componentType = value.getClass().getComponentType();
					
						if(componentType != null && componentType.isAnnotation() && componentType.isAnnotationPresent(Constraint.class)) {
							propertyAnnotations.addAll(Arrays.asList((Annotation[])value));
						}
					}
				}
			} else {
				propertyAnnotations.add(a);
			}
			
		}
		
		return propertyAnnotations;
	}
	
}
