package com.em.validation.client.services;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ValidationServiceAsync {

	public <T> void validate(T object, Class<?>[] groups, AsyncCallback<Set<ConstraintViolation<T>>> callback);
	
	public <T> void checkValidation(T object, Set<ConstraintViolation<T>> clientViolations, Class<?>[] groups, AsyncCallback<Boolean> callback);
	
}
