package com.em.validation.client.metadata;

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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

public class ConstraintDescriptorDecorator<T extends Annotation> implements ConstraintDescriptor<T> {

	private ConstraintDescriptor<T> descriptor = null;
	
	private ConstraintDescriptorDecorator() {
		
	}
	
	public ConstraintDescriptorDecorator(ConstraintDescriptor<T> descriptor) {
		this();
		
		this.descriptor = descriptor;
	}
	
	@Override
	public T getAnnotation() {
		return this.descriptor.getAnnotation();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.descriptor.getAttributes();
	}

	@Override
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return this.descriptor.getComposingConstraints();
	}

	@Override
	public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
		return this.descriptor.getConstraintValidatorClasses();
	}

	@Override
	public Set<Class<?>> getGroups() {
		return this.descriptor.getGroups();
	}

	@Override
	public Set<Class<? extends Payload>> getPayload() {
		return this.descriptor.getPayload();
	}

	@Override
	public boolean isReportAsSingleViolation() {
		return this.descriptor.isReportAsSingleViolation();
	}

	@Override
	public int hashCode() {
		return this.descriptor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof ConstraintDescriptorDecorator) {
			ConstraintDescriptorDecorator<?> input = (ConstraintDescriptorDecorator<?>)obj;
			return this.descriptor.equals(input.descriptor);
		} 
		return this.descriptor.equals(obj);
	}
	
}
