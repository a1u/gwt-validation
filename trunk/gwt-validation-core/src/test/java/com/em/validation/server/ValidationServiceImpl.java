package com.em.validation.server;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.client.services.ValidationService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ValidationServiceImpl extends RemoteServiceServlet implements ValidationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>[] groups) {
		//get the validator factory
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		//validate and return
		return validator.validate(object, groups);
	}

	@Override
	public <T> boolean checkValidation(T object, Set<ConstraintViolation<T>> clientViolations, Class<?>[] groups) {

		
		return true;
	}

	
	
}
