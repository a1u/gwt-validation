package com.em.validation.client.services;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("validation")
public interface ValidationService extends RemoteService{

	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>[] groups);
	
	public <T> boolean checkValidation(T object, Set<ConstraintViolation<T>> clientViolations, Class<?>[] groups);
	
}
