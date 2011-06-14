package com.em.validation.client;

import javax.validation.MessageInterpolator.Context;
import javax.validation.metadata.ConstraintDescriptor;

public class MessageInterpolatorContextImpl implements Context {

	private ConstraintDescriptor<?> constraintDescriptor = null;
	
	private Object value = null;
	
	public void setConstraintDescriptor(ConstraintDescriptor<?> constraintDescriptor) {
		this.constraintDescriptor = constraintDescriptor;
	}

	public void setValidatedValue(Object value) {
		this.value = value;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return constraintDescriptor;
	}

	@Override
	public Object getValidatedValue() {
		return value;
	}

}
