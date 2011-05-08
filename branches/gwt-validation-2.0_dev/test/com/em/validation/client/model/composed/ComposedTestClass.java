package com.em.validation.client.model.composed;

public class ComposedTestClass {

	@ComposedConstraint
	@ComposedSingleViolationConstraint
	private String composedString = "";

	public String getComposedString() {
		return composedString;
	}

	public void setComposedString(String composedString) {
		this.composedString = composedString;
	}
}
