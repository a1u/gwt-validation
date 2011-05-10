package com.em.validation.rebind.metadata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OverridesMetadata {
	
	/**
	 * The map that relates property names to their override value 
	 */
	private Map<Class<?>, Map<String,Object>> overrides = new HashMap<Class<?>, Map<String,Object>>();
	
	/**
	 * Map to the string values for use in code generation
	 * 
	 */
	private Map<Class<?>, Map<String,String>> overridesAsString = new HashMap<Class<?>, Map<String,String>>();
	
	/**
	 * The map that relates a value to a specific override instance in a list
	 */
	private Map<Class<?>, Map<String,Integer>> overrideIndex = new HashMap<Class<?>, Map<String,Integer>>();


	/**
	 * Add the metadata from an OverridesAttribute line to this class
	 * 
	 * @param targetAnotationClass
	 * @param propertyToOverride
	 * @param value
	 * @param valueString
	 * @param index
	 */
	public void addOverride(Class<?> targetAnotationClass, String propertyToOverride, Object value, String valueString, int index) {
		Map<String,Object> objectMap = this.overrides.get(targetAnotationClass);
		Map<String,String> stringMap = this.overridesAsString.get(targetAnotationClass);
		Map<String,Integer> intMap = this.overrideIndex.get(targetAnotationClass);
		
		//create and store object map
		if(objectMap == null) {
			objectMap = new HashMap<String, Object>();
			this.overrides.put(targetAnotationClass, objectMap);
		}
		
		//create and store string map
		if(stringMap == null) {
			stringMap = new HashMap<String, String>();
			this.overridesAsString.put(targetAnotationClass, stringMap);
		}
		
		//create and store int map
		if(intMap == null) {
			intMap = new HashMap<String, Integer>();
			this.overrideIndex.put(targetAnotationClass,intMap);
		}
		
		//set values
		objectMap.put(propertyToOverride, value);
		stringMap.put(propertyToOverride, valueString);
		intMap.put(propertyToOverride, index);
	}
	
	public Object getValue(Class<?> targetClass, String property) {
		Object value = null;
		if(this.overrides.containsKey(targetClass)) {
			value = this.overrides.get(targetClass).get(property);
		}		
		return value;
	}
	
	public String getValueString(Class<?> targetClass, String property) {
		String value = "null";
		if(this.overridesAsString.containsKey(targetClass)) {
			value = this.overridesAsString.get(targetClass).get(property);
			if(value == null) {
				value = "null";
			}
		}		
		return value;
	}
	
	public int getIndex(Class<?> targetClass, String property) {
		int index = -1;
		if(this.overrideIndex.containsKey(targetClass)) {
			Integer indexInteger = this.overrideIndex.get(targetClass).get(property);
			if(indexInteger == null) {
				index = -1;
			} else {
				index = indexInteger.intValue();
			}
		}		
		return index;
	}
	
	public Set<Class<?>> getTargetAnnotations() {
		return this.overrides.keySet();
	}
	
	public Set<String> getOverridenProperties(Class<?> targetClass) {
		Set<String> properties = new HashSet<String>();
		if(this.overrides.containsKey(targetClass)) {
			properties.addAll(this.overrides.get(targetClass).keySet());
		}
		return properties;
	}
}
