package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;

public class ReflectiveMiddle extends ReflectiveBase {

	@NotNull
	private String reflectiveMiddleString = null;

	public String getReflectiveMiddleString() {
		return reflectiveMiddleString;
	}

	public void setReflectiveMiddleString(String reflectiveMiddleString) {
		this.reflectiveMiddleString = reflectiveMiddleString;
	}
		
}
