package com.em.validation.rebind.metadata;

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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;

import com.em.validation.client.metadata.AbstractConstraintDescriptor;

public class RuntimeConstraintDescriptor<T extends Annotation> extends AbstractConstraintDescriptor<T> {

	private ConstraintMetadata annotationMetadata = null;
	
	@SuppressWarnings("unchecked")
	public RuntimeConstraintDescriptor(ConstraintMetadata annotationMetadata) {
		this.annotation = (T)annotationMetadata.getInstance();
		this.annotationMetadata = annotationMetadata;
		this.reportAsSingleViolation = annotationMetadata.isReportAsSingleViolation();
		
		for(Method method : this.annotation.getClass().getDeclaredMethods()) {
			try {
				Object value = method.invoke(this.annotation, new Object[]{});
				this.propertyMap.put(method.getName(), value);
			} catch (Exception ex) {

			}
		}
	}
	
	public void init() {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
		List<Class<? extends ConstraintValidator<T, ?>>> result = new ArrayList<Class<? extends ConstraintValidator<T,?>>>();
		for(Class<? extends ConstraintValidator<?, ?>> cv : this.annotationMetadata.getValidatedBy()) {
			try {
				result.add((Class<? extends ConstraintValidator<T, ?>>) cv);
			} catch (Exception ex) {
				//do not add if the cast fails
			}
		}		
		return result;
	}
}
