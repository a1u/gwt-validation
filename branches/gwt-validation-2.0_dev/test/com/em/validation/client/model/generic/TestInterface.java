package com.em.validation.client.model.generic;

import javax.validation.constraints.NotNull;

public interface TestInterface {

	@NotNull
	public String getTestInterfaceString();
	
}
