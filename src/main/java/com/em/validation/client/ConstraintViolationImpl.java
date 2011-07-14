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

	private Object invalidValue = null;
	
	private ConstraintDescriptor<?> constraintDescriptor = null;
	
	private Path path = new PathImpl();  
	
	private Class<T> rootBeanClass = null;
	
	private T rootBean = null;
	
	private Object leafBean = null;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	
	public void setInvalidValue(Object value) {
		this.invalidValue = value;
	}
	
	public void setConstraintDescriptor(ConstraintDescriptor<?> constraintDescriptor) {
		this.constraintDescriptor = constraintDescriptor;
	}
	
	public void setRootBean(T rootBean) {
		this.rootBean = rootBean;
	}
	
	public void setRootBeanClass(Class<T> rootBeanClass) {
		this.rootBeanClass = rootBeanClass;
	}
	
	public void setLeafBean(Object leafBean) {
		this.leafBean = leafBean;
	}
	
	public void setPropertyPath(Path path) {
		this.path = path;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessageTemplate() {
		return messageTemplate;
	}

	@Override
	public T getRootBean() {
		return this.rootBean;
	}

	@Override
	public Class<T> getRootBeanClass() {
		return this.rootBeanClass;
	}

	@Override
	public Object getLeafBean() {
		return this.leafBean;
	}

	@Override
	public Path getPropertyPath() {
		return this.path;
	}

	@Override
	public Object getInvalidValue() {
		return this.invalidValue;
	}

	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return this.constraintDescriptor;
	}

}
