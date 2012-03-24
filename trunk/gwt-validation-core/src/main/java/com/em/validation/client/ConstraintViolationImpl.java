package com.em.validation.client;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

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

	private transient Object invalidValue = null;
	
	private ConstraintDescriptor<?> constraintDescriptor = null;
	
	private Path path = new PathImpl();  
	
	private transient Class<T> rootBeanClass = null;
	
	private transient T rootBean = null;
	
	private transient Object leafBean = null;
	
	public ConstraintViolationImpl() {
		super();
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((invalidValue == null) ? 0 : invalidValue.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result
				+ ((rootBean == null) ? 0 : rootBean.hashCode());
		result = prime * result
				+ ((rootBeanClass == null) ? 0 : rootBeanClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ConstraintViolationImpl)) {
			return false;
		}
		ConstraintViolationImpl<?> other = (ConstraintViolationImpl<?>) obj;
		if (invalidValue == null) {
			if (other.invalidValue != null) {
				return false;
			}
		} else if (!invalidValue.equals(other.invalidValue)) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (rootBean == null) {
			if (other.rootBean != null) {
				return false;
			}
		} else if (!rootBean.equals(other.rootBean)) {
			return false;
		}
		if (rootBeanClass == null) {
			if (other.rootBeanClass != null) {
				return false;
			}
		} else if (!rootBeanClass.equals(other.rootBeanClass)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" = [");
		if(this.getConstraintDescriptor() != null && this.getConstraintDescriptor().getAnnotation() != null) {
			builder.append(this.getConstraintDescriptor().getAnnotation().annotationType().getName());	
		} else {
			builder.append(super.toString());
		}
		if(this.path != null && this.path.toString() != null && !this.path.toString().isEmpty()) {
			builder.append(", path = ");
			builder.append(this.path.toString());
		}
		if(this.invalidValue != null) {
			builder.append(", value = ");
			builder.append(this.invalidValue.toString());
		} else {
			builder.append(", value = null");
		}
		builder.append("]");
		return builder.toString();		
	}
	
}
