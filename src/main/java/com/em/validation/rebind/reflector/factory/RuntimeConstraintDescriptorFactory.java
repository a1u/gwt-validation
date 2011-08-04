package com.em.validation.rebind.reflector.factory;

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
import java.util.HashMap;
import java.util.Map;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.metadata.ConstraintDescriptorDecorator;
import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.RuntimeConstraintDescriptor;

public enum RuntimeConstraintDescriptorFactory {
	
	INSTANCE;

	private Map<String, ConstraintDescriptor<?>> descriptorCache = new HashMap<String, ConstraintDescriptor<?>>();
	
	private RuntimeConstraintDescriptorFactory() {
		
	}
	
	public ConstraintDescriptor<?> getConstraintDescriptor(ConstraintMetadata metadata) {
		String key = metadata.toString();
		
		ConstraintDescriptor<?> descriptor = this.descriptorCache.get(key);

		boolean noSpecificType = false;
		
		if(descriptor == null) {
			noSpecificType = true;
		}
		
		if(descriptor == null) {
			descriptor = new RuntimeConstraintDescriptor<Annotation>(metadata);
			
			if(noSpecificType && !this.descriptorCache.containsKey(metadata.getInstance().toString())) {
				this.descriptorCache.put(metadata.getInstance().toString(), descriptor);
			} 
			
			this.descriptorCache.put(key, descriptor);
			
			for(ConstraintMetadata sub : metadata.getComposedOf()) {
				descriptor.getComposingConstraints().add(this.getConstraintDescriptor(sub));
			}
		}
		
		//decorate the result so that each instance will appear unique.  this means that the constraints will be counted 
		//correctly and an instance will be returned for each constraint annotation.  this is a workaround introduced 
		//because of the caching and reuse of the instances based on the signature.
		@SuppressWarnings("unchecked")
		ConstraintDescriptorDecorator<Annotation> decorator = new ConstraintDescriptorDecorator<Annotation>((ConstraintDescriptor<Annotation>)descriptor);
		return decorator;
	}
	
}
