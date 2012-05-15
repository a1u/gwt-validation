package com.em.validation.client.model.defects.defect_069;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ValidConstraintTestClass {

	
	@Valid
	@NotNull
	private ValidConstraintChildClass childOne;
	
	private ValidConstraintChildClass childTwo;
	
	public ValidConstraintChildClass getChildOne() {
		return childOne;
	}

	public void setChildOne(ValidConstraintChildClass childOne) {
		this.childOne = childOne;
	}

	@Valid
	@NotNull
	public ValidConstraintChildClass getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(ValidConstraintChildClass childTwo) {
		this.childTwo = childTwo;
	}
}
