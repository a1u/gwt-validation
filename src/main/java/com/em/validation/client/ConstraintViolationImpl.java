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

	@Override
	public String toString() {
		if(this.getConstraintDescriptor() != null && this.getConstraintDescriptor().getAnnotation() != null) {
			return this.getConstraintDescriptor().getAnnotation().annotationType().getClass().getName();	
		} 
		return super.toString();		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((invalidValue == null) ? 0 : invalidValue.hashCode());
		result = prime * result
				+ ((leafBean == null) ? 0 : leafBean.hashCode());
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
		if (leafBean == null) {
			if (other.leafBean != null) {
				return false;
			}
		} else if (!leafBean.equals(other.leafBean)) {
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
	
}
