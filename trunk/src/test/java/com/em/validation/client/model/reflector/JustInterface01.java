package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface JustInterface01 extends JustBaseInterface {

	@NotNull
	@Size(max=2)
	public String getInterface01String();
	
}
