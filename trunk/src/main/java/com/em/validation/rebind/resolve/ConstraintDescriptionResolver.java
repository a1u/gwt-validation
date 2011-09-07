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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.OverridesAttribute;
import javax.validation.ReportAsSingleViolation;
import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.ConstraintPropertyMetadata;
import com.em.validation.rebind.metadata.OverridesMetadata;
import com.em.validation.rebind.metadata.OverridesMetadata.OverrideValues;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.reflector.AnnotationProxyFactory;
import com.em.validation.rebind.reflector.factory.RuntimeConstraintDescriptorFactory;


/**
 * Get all of the constraints (via constraint descriptors) of a given class.
 * 
 * @author chris
 *
 */
public enum ConstraintDescriptionResolver {

	INSTANCE;
	
	private Map<String, ConstraintMetadata> metadataCache = new HashMap<String, ConstraintMetadata>();
	
	private ConstraintDescriptionResolver() {
		
	}
	
	public Map<String,Set<ConstraintDescriptor<?>>> getConstraintDescriptors(Class<?> targetClass) {
		Map<String,Set<ConstraintDescriptor<?>>> results = new HashMap<String, Set<ConstraintDescriptor<?>>>();
		Map<String,PropertyMetadata> propertyMetadata = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);

		//get class level annotations and get the constraint metadata and add them
		List<Annotation> classLevelAnnotations = PropertyResolver.INSTANCE.getContstraintAnnotations(Arrays.asList(targetClass.getAnnotations()));
		Set<ConstraintDescriptor<?>> classLevelDescriptors = new LinkedHashSet<ConstraintDescriptor<?>>();
		for(Annotation classAnnotation : classLevelAnnotations) {
			ConstraintMetadata metadata = this.getConstraintMetadata(classAnnotation,targetClass);
			ConstraintDescriptor<?> descriptor = RuntimeConstraintDescriptorFactory.INSTANCE.getConstraintDescriptor(metadata);
			classLevelDescriptors.add(descriptor);
		}
		//add class level metadata to return map
		results.put(targetClass.getName(), classLevelDescriptors);		
		
		for(String propertyName : propertyMetadata.keySet()) {
			results.put(propertyName, this.getConstraintsForProperty(targetClass, propertyName));
		}		
		return results;
	}
	
	public Set<ConstraintMetadata> getAllMetadata(Class<?> targetClass) {
		Set<ConstraintMetadata> metadataResult = new LinkedHashSet<ConstraintMetadata>();
		Map<String,PropertyMetadata> propertyMetadata = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
		for(String propertyName : propertyMetadata.keySet()) {
			PropertyMetadata property = propertyMetadata.get(propertyName);
			for(Annotation annotation : property.getAnnotationInstances()) {
				metadataResult.add(this.getConstraintMetadata(annotation,property.getReturnType()));
			}
		}		
		return metadataResult;
	}
	
	public Set<ConstraintDescriptor<? extends Annotation>> getConstraintsForProperty(Class<?> targetClass, String propertyName) {
		Set<ConstraintDescriptor<?>> descriptors = new LinkedHashSet<ConstraintDescriptor<?>>();
		PropertyMetadata property = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass, propertyName);
		for(Annotation annotation : property.getAnnotationInstances()) {
			ConstraintMetadata metadata = this.getConstraintMetadata(annotation,property.getReturnType());
			ConstraintDescriptor<?> descriptor = RuntimeConstraintDescriptorFactory.INSTANCE.getConstraintDescriptor(metadata);
			descriptors.add(descriptor);
		}		
		return descriptors;
	}
	
	@SuppressWarnings("unchecked")
	public ConstraintMetadata getConstraintMetadata(Annotation annotation, Class<?> elementType) {
		
		//key
		String key = annotation.toString();
		if(elementType != null) {
			key = key + elementType.getName();
		}
		
		//create annotation metadata
		ConstraintMetadata metadata = this.metadataCache.get(key); 
				
		//if the cache misses, generate
		if(metadata == null) {
			metadata = new ConstraintMetadata();
		
			//create empty annotation metadata
			//annotation names
			metadata.setName(annotation.annotationType().getName());
			metadata.setSimpleName(annotation.annotationType().getSimpleName());
			metadata.setInstance(annotation);
			metadata.setTargetClass(elementType);
			
			//create annotation method metadata
			for(Method method : annotation.annotationType().getDeclaredMethods()) {
				ConstraintPropertyMetadata aMeta = new ConstraintPropertyMetadata();
				String returnValue = this.createReturnValueAsString(method, annotation);
				
				//get return type
				Class<?> returnClass = method.getReturnType();
				String importType = null;
				int index = 0;

				//find innermost contained class
				while(returnClass.getComponentType() != null) {
					returnClass = returnClass.getComponentType();
					index++;
				}
				String returnType = returnClass.getSimpleName();
				
				StringBuilder suffixBuilder = new StringBuilder("");
				for(int i = 0; i < index; i++) {
					suffixBuilder.append("[]");
				}				
				returnType = returnType + suffixBuilder.toString();
				aMeta.setReturnType(returnType);
				
				//primitive classes do not need to be imported
				if(!returnClass.isPrimitive()) {
					importType = returnClass.getName();
				}
				
				//only handle imported values that have an actual value
				if(importType != null) {
					importType = importType.replaceAll("\\$", ".");
					aMeta.setImportType(importType);
				}				
				
				//set the method name and return value
				aMeta.setMethodName(method.getName());
				aMeta.setReturnValue(returnValue);		
				
				//save metadata for use by generator
				metadata.getMethodMap().put(aMeta.getMethodName(), aMeta);
			}
			
			//constraint valdiators
			for(Class<?> validatorClass : ValidatorResolver.INSTANCE.getValidatorClassesForAnnotation(annotation.annotationType(), elementType)) {
				metadata.getValidatedBy().add((Class<? extends ConstraintValidator<?, ?>>)validatorClass);	
			}			
			
			//report as single or not
			metadata.setReportAsSingleViolation(annotation.annotationType().getAnnotation(ReportAsSingleViolation.class) != null);  
			
			//put in cache
			this.metadataCache.put(key, metadata);
			
			//get composing constraints
			for(Annotation subAnnotation : annotation.annotationType().getAnnotations()) {
				if(subAnnotation.annotationType().getAnnotation(Constraint.class) != null){
					metadata.getComposedOf().add(this.getConstraintMetadata(subAnnotation,elementType));
				} else {
					try {
						//if the type isn't a @Constraint annotated class then check the value
						//method as we would with other constraints to see if it is a .List
						Method valueMethod = subAnnotation.annotationType().getMethod("value", new Class<?>[]{});
						if(valueMethod != null && valueMethod.getReturnType().getComponentType() != null) {
							Object[] componentConstraints = (Object[])valueMethod.invoke(subAnnotation, new Object[]{});
							//confusing, i know, but these are sub annotations to the sub annotations
							for(Object subSubObject : componentConstraints) {
								if(subSubObject instanceof Annotation) {
									Annotation subSubAnnotation = (Annotation)subSubObject;
									if(subSubAnnotation.annotationType().getAnnotation(Constraint.class) != null) {
										metadata.getComposedOf().add(this.getConstraintMetadata(subSubAnnotation,elementType));
									}
								}
							}
						}
					} catch (Exception ex) {
						//no value method exists
					}
				}
			}
			
			//get overrides
			if(metadata.getComposedOf() != null && metadata.getComposedOf().size() > 0) {
				//create overrides metadata for method/property
				OverridesMetadata overrides = new OverridesMetadata();
				//create annotation method metadata
				for(Method method : annotation.annotationType().getDeclaredMethods()) {
					OverridesAttribute override = method.getAnnotation(OverridesAttribute.class);
					if(override != null) {
						Object value = null;
						try {
							value = method.invoke(annotation, new Object[]{});
						} catch (Exception e) {
							//could not invoke method, value is null
						}						
						overrides.addOverride(override.constraint(), override.name(), value, this.createReturnValueAsString(value), override.constraintIndex());
					}
					OverridesAttribute.List overrideList = method.getAnnotation(OverridesAttribute.List.class);
					if(overrideList != null && overrideList.value() != null && overrideList.value().length > 0) {
						for(OverridesAttribute listedOverride : overrideList.value()) {
							if(listedOverride != null) {
								Object value = null;
								try {
									value = method.invoke(annotation, new Object[]{});
								} catch (Exception e) {
									//could not invoke method, value is null
								}						
								overrides.addOverride(listedOverride.constraint(), listedOverride.name(), value, this.createReturnValueAsString(value), listedOverride.constraintIndex());
							}							
						}
					}
				}
				
				//update composed instances with overrides metadata
				Map<Class<?>,Integer> oIndexMap = new HashMap<Class<?>, Integer>();
				for(ConstraintMetadata desc : metadata.getComposedOf()) {
					//get instance
					Annotation instance = desc.getInstance();
					
					//get index for the annotation type
					Integer oIndex = oIndexMap.get(instance.annotationType());
					if(oIndex == null) {
						oIndex = 0;
					}
					int index = oIndex.intValue();
					
					//get the overridden properties
					Set<String> props = overrides.getOverridenProperties(instance.annotationType());
					
					//create override map
					Map<String,Object> overrideMap = new HashMap<String, Object>();
					
					//cycle through them and add them to the override map
					for(String prop : props) {
						List<OverrideValues> propOverrides = overrides.getOverrideValues(instance.annotationType(), prop);
						for(OverrideValues oValue : propOverrides) {
							//if the index matches the index of the class or the index is -1, then apply
							if(index == oValue.getIndex() || oValue.getIndex() == -1) {
								//set override in property map
								overrideMap.put(prop, oValue.getValue());
								//set override in method map
								ConstraintPropertyMetadata pMeta = desc.getMethodMap().get(prop);
								pMeta.setReturnValue(oValue.getValueAsString());
							}
						}
					}					
					
					//if the override map has a size > 0, then use it as the override for the proxy
					if(overrideMap.size() > 0) {
						Annotation newAnnotationInstance = AnnotationProxyFactory.INSTANCE.getProxy(instance, overrideMap);
						desc.setInstance(newAnnotationInstance);
					}
					
					//increment and put away index
					index++;
					oIndexMap.put(instance.annotationType(), index);
				}
				
			}			
		}
		
		return metadata;
	}
		
	/**
	 * Takes an annotation and a method and turns it into the string representation of what
	 * would need to be typed in to a class body to return that value;
	 * 
	 * @param method
	 * @param annotation
	 * @return
	 */
	private String createReturnValueAsString(Method method, Annotation annotation) {
		//invoke method
		Object value = null;

		try {
			value = method.invoke(annotation, new Object[]{});
		} catch (Exception e) {
		} 
		
		//use the return value function to resolve the string.
		return this.createReturnValueAsString(value);
	}
	
	/**
	 * Takes a human readable / string translatable value of the type that would be
	 * found on an annotation and turns it into a string that can be used in a code
	 * generation template.
	 * 
	 * @param method
	 * @param annotation
	 * @return
	 */
	private String createReturnValueAsString(Object value) {
		//return a null value, as a string, so that it is printed as plain null
		//this will preserve whatever the class is doing so that no "inexplicable"
		//changes are made.
		if(value == null) return "null";

		Class<?> returnType = value.getClass();
		Class<?> containedClass = returnType.getComponentType();
		
		StringBuilder output = new StringBuilder();
		if(containedClass == null) {
			if(String.class.equals(returnType)) {
				output.append("\"");
			} 
			
			if(returnType.isEnum()) {
				output.append(returnType.getSimpleName());
				output.append(".");
			}
			
			if(value instanceof Class<?>) {
				Class<?> clazz = (Class<?>)value;
				output.append(clazz.getName() + ".class");
			} else {				
				output.append(value);
			}
			
			if(String.class.equals(returnType)) {
				output.append("\"");
			}
		} else {
			//get the array since the return type is of a container 
			Object[] values = (Object[])value;
			
			output.append("new ");
			output.append(containedClass.getSimpleName());
			output.append("[]{");
			int i = 0;
			for(Object v : values) {
				if(i > 0) {
					output.append(",");
				}
				i++;
				if(String.class.equals(containedClass)) {
					output.append("\"");
				} 
				if(v instanceof Class<?>) {
					Class<?> clazz = (Class<?>)v;
					output.append(clazz.getName() + ".class");
				} else {				
					output.append(v);
				}
				if(String.class.equals(containedClass)) {
					output.append("\"");
				}
			}
			output.append("}");
		}		
		
		return output.toString();
	}
	
}
