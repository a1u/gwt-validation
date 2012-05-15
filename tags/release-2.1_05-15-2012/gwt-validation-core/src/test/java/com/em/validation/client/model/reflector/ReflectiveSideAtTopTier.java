package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ReflectiveSideAtTopTier {

	@NotNull
	@Size(max=10)
	public String getReflectiveSideAtTopTierString();
	
}
