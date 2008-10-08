package com.google.gwt.validation.client.test;

import java.util.Date;

import com.google.gwt.validation.client.Future;

public interface TestSuperInterface {

	@Future(groups={"default", "five"})
	public Date failFutureDate();
	
}
