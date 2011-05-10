package com.em.validation.rebind.metadata;

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
		
		for(Method method : this.annotation.getClass().getMethods()) {
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
