package com.em.validation.client;

import java.io.Serializable;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class ConstraintViolationImpl<T> implements ConstraintViolation<T>, Serializable {

	/**
	 * Constraint violations should be serializable and thus, need a version id
	 */
	private static final long serialVersionUID = 1L;

	
	private String message = "";
	
	private String messageTemplate = "";

	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageTemplate() {
		// TODO Auto-generated method stub
		return messageTemplate;
	}

	@Override
	public T getRootBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<T> getRootBeanClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLeafBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getPropertyPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getInvalidValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

}
