package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;

public class ReflectiveBase {

	@NotNull
	private String reflectiveBaseString = null;

	public String getReflectiveBaseString() {
		return reflectiveBaseString;
	}

	public void setReflectiveBaseString(String reflectiveBaseString) {
		this.reflectiveBaseString = reflectiveBaseString;
	}
	
}
