package com.google.gwt.validation.client.jsr303;

import javax.validation.constraints.Size;


public class SizeValidator extends com.google.gwt.validation.client.SizeValidator {

	public SizeValidator() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void initialize(Size constraintAnnotation) {
		this.minimum = constraintAnnotation.min();
		this.maximum = constraintAnnotation.max();
	}
}
