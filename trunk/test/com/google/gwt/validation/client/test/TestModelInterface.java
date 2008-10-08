package com.google.gwt.validation.client.test;

import java.util.Date;

import com.google.gwt.validation.client.Past;

public interface TestModelInterface {

	@Past(groups={"default", "one"})
	public Date notInFuture();
	
}
