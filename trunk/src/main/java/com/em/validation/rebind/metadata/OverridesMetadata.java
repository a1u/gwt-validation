package com.em.validation.rebind.metadata;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OverridesMetadata {
	
	public static class OverrideValues {
		private String valueAsString = "null";
		private Object value = null;
		private int index = -1;
		private String propertyName = null;
		
		public String getValueAsString() {
			return valueAsString;
		}
		public void setValueAsString(String valueAsString) {
			this.valueAsString = valueAsString;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getPropertyName() {
			return propertyName;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}		
	}
	
	Map<Class<?>,List<OverrideValues>> metadataMap = new HashMap<Class<?>, List<OverrideValues>>();
	
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
		//create value container object to place in map
		OverrideValues values = new OverrideValues();
		values.setIndex(index);
		values.setValueAsString(valueString);
		values.setValue(value);
		values.setPropertyName(propertyToOverride);
		
		//get list from metadata map that represents the list of overrides for the given class
		List<OverrideValues> valueList = this.metadataMap.get(targetAnotationClass);
		if(valueList == null) {
			valueList = new ArrayList<OverridesMetadata.OverrideValues>();
			this.metadataMap.put(targetAnotationClass, valueList);
		}
		
		//add to value list
		valueList.add(values);
	}
	
	public List<OverrideValues> getOverrideValues(Class<?> targetClass, String property) {
		List<OverrideValues> list = this.metadataMap.get(targetClass);
		List<OverrideValues> result = new ArrayList<OverridesMetadata.OverrideValues>();
		if(list != null) {
			for(OverrideValues value : list) {
				if(property.equals(value.getPropertyName())) {
					result.add(value);
				}
			}
		}		
		return result;
	}
	
	
	public Set<String> getOverridenProperties(Class<?> targetClass) {
		Set<String> properties = new HashSet<String>();
		List<OverrideValues> list = this.metadataMap.get(targetClass);
		if(list != null) {
			for(OverrideValues value : list) {
				properties.add(value.getPropertyName());
			}
		}
		return properties;
	}
}
