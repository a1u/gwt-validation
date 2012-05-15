package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ReflectiveSide02AtIntermediaryLayer {

	@NotNull
	@Size(max=5)
	public String getReflectiveSide02AtIntermediaryLayerString();
	
}
