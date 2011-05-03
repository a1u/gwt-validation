package com.em.validation.rebind;

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;

import com.em.validation.client.reflector.IReflector;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.metadata.ReflectorClassDescriptions;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public enum ReflectorGenerator {

	INSTANCE;
	
	public ReflectorClassDescriptions getReflectorDescirptions(Class<?> reflectionTarget) {
		
		//setup freemarker
		Configuration freemarkerConfig = new Configuration();
		freemarkerConfig.setClassForTemplateLoading(IReflector.class, "");
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
		
		//get the basic name of the target class and generate the reflector class name
		String concreteClassName = reflectionTarget.getSimpleName() + "Reflector";
		
		//set up imports
		List<String> imports = new ArrayList<String>();
		imports.add(reflectionTarget.getName());
		
		Set<PropertyDescriptor> propertyDescriptors = new LinkedHashSet<PropertyDescriptor>();
		//get the property descriptors for the given target class
		BeanInfo targetInfo = null;
		try {
			targetInfo = Introspector.getBeanInfo(reflectionTarget);
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		//save off the info 
		propertyDescriptors.addAll(Arrays.asList(targetInfo.getPropertyDescriptors()));		
		
		//this will be used to filter against later in the process.  this will allow us to
		//know if a property has a publicly accessible field or if it has a method that should
		//be used instead.
		HashMap<String,Field> publicFields = new HashMap<String, Field>();
		for(Field field : reflectionTarget.getFields()) {
			publicFields.put(field.getName(),field);
		}
		
		//the list of properties
		List<PropertyMetadata> metadataList = new ArrayList<PropertyMetadata>();
		
		//the source listings
		ReflectorClassDescriptions classDescriptions = new ReflectorClassDescriptions();
		
		//save the property and it's proper accessor to the property metadata list;
		for(PropertyDescriptor property : propertyDescriptors) {
		
			String propertyName = property.getName();
			
			//skip the "class" property.  we can't really validate that anyway.
			if("class".equals(propertyName)) continue;

			PropertyMetadata pMeta = new PropertyMetadata();
			pMeta.setName(propertyName);
			
			if(this.hasField(reflectionTarget, propertyName)) {
				pMeta.setAccessor(propertyName);
			} else if(this.hasMethod(reflectionTarget, property.getReadMethod().getName(), new Class<?>[]{})) {
				pMeta.setAccessor(property.getReadMethod().getName() + "()");
			} else {
				continue;
			}
			
			//add the property to the metadata list
			metadataList.add(pMeta);
			//remove accessible field from public fields, so that it won't be processed twice
			publicFields.remove(propertyName);
			
			//collect the method and field annotations
			Set<Annotation> potentialAnnotations = new LinkedHashSet<Annotation>();

			try {
				Field field = reflectionTarget.getField(propertyName);
				potentialAnnotations.addAll(Arrays.asList(field.getAnnotations()));
			} catch (Exception ex) {
				//log no field access
			}
			
			//recursively resolve all annotations for the method on the given class
			try {
				Method method = property.getReadMethod();
				String methodName = property.getReadMethod().getName();
				Class<?> infoClass = method.getDeclaringClass();
				do {
					for(Class<?> infoInterface : infoClass.getInterfaces()) {
						if(this.hasMethod(infoInterface, methodName, new Class<?>[]{})) {
							Method interfaceMethod = infoInterface.getMethod(methodName, new Class<?>[]{});
							potentialAnnotations.addAll(Arrays.asList(interfaceMethod.getAnnotations()));
						}
					}
					potentialAnnotations.addAll(Arrays.asList(method.getAnnotations()));
					infoClass = infoClass.getSuperclass();
					if(this.hasMethod(infoClass, methodName, new Class<?>[]{})) {
						method = infoClass.getMethod(methodName, new Class<?>[]{});
					}
				} while(infoClass != null);
			} catch (Exception ex) {
				//log no method access (?)
			}
			
			List<Annotation> annotations = this.getContstraintAnnotations(new ArrayList<Annotation>(potentialAnnotations));
			
			for(Annotation annotation : annotations) {
				ClassDescriptor description = ConstraintDescriptionGenerator.INSTANCE.generateConstraintAnnotation(reflectionTarget, propertyName, annotation);
				pMeta.getAnnotations().add(description.getFullClassName());
				classDescriptions.getConstraintDescriptors().add(description);
			}
		}
		
		//process remaining fields
		for(String fieldName : publicFields.keySet()) {

			//add reflective bits
			PropertyMetadata pMeta = new PropertyMetadata();
			pMeta.setName(fieldName);
			pMeta.setAccessor(fieldName);
			metadataList.add(pMeta);
			
			//get field
			Field field = publicFields.get(fieldName);
			
			//process annotations
			List<Annotation> annotations = this.getContstraintAnnotations(Arrays.asList(field.getAnnotations()));
			
			for(Annotation annotation : annotations) {
				ClassDescriptor description = ConstraintDescriptionGenerator.INSTANCE.generateConstraintAnnotation(reflectionTarget, fieldName, annotation);
				pMeta.getAnnotations().add(description.getFullClassName());
				classDescriptions.getConstraintDescriptors().add(description);
			}
		}
		
		String targetPackage = "com.em.validation.client.reflector.generated.reflector";
		
		//create data model
		Map<String,Object> templateDataModel = new HashMap<String, Object>();
		templateDataModel.put("concreteClassName", concreteClassName);
		templateDataModel.put("importList", imports);
		templateDataModel.put("properties", metadataList);
		templateDataModel.put("reflectionTargetName", reflectionTarget.getSimpleName());
		templateDataModel.put("targetPackage", targetPackage);
		templateDataModel.put("generatedConstraintPackage",ConstraintDescriptionGenerator.INSTANCE.getTargetPackage());
		
		ClassDescriptor reflectorDescriptor = new ClassDescriptor();
		reflectorDescriptor.setClassContents(TemplateController.INSTANCE.processTemplate("templates/reflector/ReflectorImpl.ftl", templateDataModel));
		reflectorDescriptor.setClassName(concreteClassName);
		reflectorDescriptor.setFullClassName(targetPackage + "." + concreteClassName);
		classDescriptions.setClassDescriptor(reflectorDescriptor);
		
		return classDescriptions;
	}
	
	private boolean hasField(Class<?> targetClass, String targetFieldName) {
		try {
			if(targetClass.getField(targetFieldName) != null) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		return false;
	}

	private boolean hasMethod(Class<?> targetClass, String targetFieldName, Class<?>[] parameterTypes) {
		try {
			if(targetClass.getMethod(targetFieldName,parameterTypes) != null) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return false;
	}

	private List<Annotation> getContstraintAnnotations(List<Annotation> inputList) {
		
		//the list of approved/correct annotations
		List<Annotation> propertyAnnotations = new ArrayList<Annotation>();
		
		for(Annotation a : inputList) {
			//get the annotation type
			Class<? extends Annotation> annotationType = a.annotationType();
			
			//potential annotations that have a constraint defined continue to the next round of processing
			if(!annotationType.isAnnotationPresent(Constraint.class)) {
				Method valueMethod = null;
				try {
					valueMethod = annotationType.getMethod("value", new Class<?>[]{});
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
