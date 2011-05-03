package com.em.validation.client.model.generic;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ExtendedInterface extends TestInterface {
	
	@AssertTrue
	public boolean isTrue();
	
	@AssertFalse
	public boolean isFalse();
	
	@NotNull
	@Size(min=1)
	public String getString();
}
