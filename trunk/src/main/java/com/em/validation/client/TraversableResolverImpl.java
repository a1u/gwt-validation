package com.em.validation.client;

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
		
		//get topmost node
		Node topNode = null;
		for(Node node : pathToTraversableObject) {
			if(node != null) {
				topNode = node;
			}
		}
		
		//begin to find out what type of property it is and to what point the caller is trying to traverse
		if(traversableObject instanceof Map) {
			Object key = topNode.getKey();
			return ((Map<?,?>) traversableObject).containsKey(key);			
		} else if(traversableObject instanceof List) {
			int index = topNode.getIndex();
			return ((List<?>)traversableObject).size() > index;
		} else if(traversableObject instanceof Object[]) {
			int index = topNode.getIndex();
			return ((Object[])traversableObject).length > index;
		} else {
			String propertyName = topNode.getName();
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
		//the line below caused part of defect #42, please re-evaluate
		//return this.isReachable(traversableObject, traversableProperty, rootBeanType, pathToTraversableObject, elementType);
		return true;
	}

}
