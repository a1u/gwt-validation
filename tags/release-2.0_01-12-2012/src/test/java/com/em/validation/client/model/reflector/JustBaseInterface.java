package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface JustBaseInterface {
	
	@NotNull
	@Size(max=1)
	public String getJustBaseInterface();
	
}
