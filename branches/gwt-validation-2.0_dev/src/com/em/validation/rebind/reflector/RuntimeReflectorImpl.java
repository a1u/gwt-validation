package com.em.validation.rebind.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.reflector.Reflector;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.resolve.PropertyResolver;

public class RuntimeReflectorImpl<T> extends Reflector<T> {

	Map<String,PropertyMetadata> metadataMap = new HashMap<String, PropertyMetadata>();
	
	private RuntimeReflectorImpl() {
		
	}
	
	public RuntimeReflectorImpl(Class<?> targetClass) {
		this();
		this.targetClass = targetClass;
		this.metadataMap = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
	}
	
	@Override
	public Object getValue(String name, T target) {
		//get property metadata
		PropertyMetadata metadata = this.metadataMap.get(name); 
		
		Object value = null;
		
		if(metadata != null && metadata.getAccessor() != null) {
			String accssesor = metadata.getAccessor();
			if(metadata.isField()) {
				try {
					Field field = this.targetClass.getDeclaredField(accssesor);
					value = field.get(target);
				} catch (Exception ex) {
					//field not available
				}
			} else {
				try {
					accssesor = accssesor.replaceAll("\\(\\)","");
					Method method = this.targetClass.getDeclaredMethod(accssesor, new Class<?>[]{});
					value = method.invoke(target, new Object[]{});
				} catch (Exception ex) {
					//method not available
				}
			}
		}
		
		if(value == null) {
			value = this.getSuperValues(name, target);
		}
		
		return value;
	}
	

	public void setConstraintDescriptorMap(Map<String, Set<ConstraintDescriptor<?>>> constraintDescriptors) {
		this.constraintDescriptors = constraintDescriptors;
	}
}
