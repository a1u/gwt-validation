package com.em.validation.client;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Map;

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;

public class TraversableResolverImpl implements TraversableResolver {

	@Override
	public boolean isReachable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
		//don't even bother if the object is null
		if(traversableObject == null) return false;
		
		//begin to find out what type of property it is and to what point the caller is trying to traverse
		if(traversableObject instanceof Map) {
			Object key = traversableProperty.getKey();
			return ((Map<?,?>) traversableObject).containsKey(key);			
		} else if(traversableObject instanceof List) {
			int index = traversableProperty.getIndex();
			try {
				((List<?>)traversableObject).get(index);
				return true;
			} catch (Exception ex) {
				//if the index doesn't exist, throws an exception and goes on to return false
			}
		} else {
			String propertyName = traversableProperty.getName();
			BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(traversableObject);
			for(PropertyDescriptor prop : descriptor.getConstrainedProperties()) {
				if(prop.getPropertyName().equals(propertyName)) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isCascadable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
		//todo: add cascaded part
		return this.isReachable(traversableObject, traversableProperty, rootBeanType, pathToTraversableObject, elementType);
	}

}
