package com.em.validation.rebind.reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.em.validation.client.reflector.Reflector;
import com.em.validation.rebind.metadata.PropertyMetadata;
import com.em.validation.rebind.resolve.ConstraintDescriptionResolver;
import com.em.validation.rebind.resolve.PropertyResolver;

public class RuntimeReflectorImpl<T> extends Reflector<T> {

	Map<String,PropertyMetadata> metadataMap = new HashMap<String, PropertyMetadata>();
	
	private RuntimeReflectorImpl() {
		
	}
	
	public RuntimeReflectorImpl(Class<?> targetClass) {
		this();
		this.targetClass = targetClass;
		this.metadataMap = PropertyResolver.INSTANCE.getPropertyMetadata(targetClass);
		this.constraintDescriptors = ConstraintDescriptionResolver.INSTANCE.getConstraintDescriptors(targetClass);
	}
	
	@Override
	public Object getValue(String name, T target) {
		//get property metadata and stuff
		PropertyMetadata metadata = this.metadataMap.get(name); 
		Object value = null;
		if(metadata == null || metadata.getAccessor() == null) return value;
		
		String accessor = metadata.getAccessor();
		if(metadata.isField()) {
			try {
				Field field = this.targetClass.getField(accessor);
				value = field.get(target);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				accessor = accessor.replaceAll("\\(\\)","");
				Method method = this.targetClass.getMethod(accessor, new Class<?>[]{});
				value = method.invoke(target, new Object[]{});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		return value;
	}

}
