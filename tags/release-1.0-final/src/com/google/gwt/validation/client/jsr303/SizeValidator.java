package com.google.gwt.validation.client.jsr303;

import javax.validation.constraints.Size;

import com.google.gwt.validation.client.common.SizeValidatorAbstract;
import com.google.gwt.validation.client.interfaces.IConstraint;


public class SizeValidator extends SizeValidatorAbstract implements IConstraint<Size>  {

	public void initialize(Size constraintAnnotation) {
		this.minimum = constraintAnnotation.min();
		this.maximum = constraintAnnotation.max();
	}
}
