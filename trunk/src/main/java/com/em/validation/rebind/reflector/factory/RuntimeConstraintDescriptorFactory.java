package com.em.validation.rebind.reflector.factory;

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

import java.lang.annotation.Annotation;

import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.metadata.ConstraintDescriptorDecorator;
import com.em.validation.rebind.AbstractConstraintDescriptorGenerator;
import com.em.validation.rebind.metadata.ConstraintMetadata;
import com.em.validation.rebind.metadata.RuntimeConstraintDescriptor;

public enum RuntimeConstraintDescriptorFactory {
	
	INSTANCE;

	private static class RuntimeConstraintGenerator extends AbstractConstraintDescriptorGenerator<ConstraintDescriptor<?>> {

		@Override
		protected ConstraintDescriptor<?> create(ConstraintMetadata metadata) {
			return new RuntimeConstraintDescriptor<Annotation>(metadata);
		}

		@Override
		protected void recurse(ConstraintDescriptor<?> withDescriptor, ConstraintMetadata metadata) {
			withDescriptor.getComposingConstraints().add(this.getConstraintDescriptor(metadata));
		}

		@SuppressWarnings("unchecked")
		@Override
		protected ConstraintDescriptor<?> finish(ConstraintDescriptor<?> withDescriptor, ConstraintMetadata metadata) {
			return new ConstraintDescriptorDecorator<Annotation>((ConstraintDescriptor<Annotation>)withDescriptor);
		}
		
	}
	
	private RuntimeConstraintGenerator generator = null;
	
	private RuntimeConstraintDescriptorFactory() {
		this.generator = new RuntimeConstraintGenerator();
	}
	
	public ConstraintDescriptor<?> getConstraintDescriptor(ConstraintMetadata metadata) {
		return generator.getConstraintDescriptor(metadata);
	}
	
}
