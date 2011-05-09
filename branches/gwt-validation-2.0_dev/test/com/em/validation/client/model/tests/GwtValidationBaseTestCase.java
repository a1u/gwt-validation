package com.em.validation.client.model.tests;

import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;
import com.google.gwt.junit.client.GWTTestCase;

public class GwtValidationBaseTestCase extends GWTTestCase implements ITestCase{
	
	@Override
	public String getModuleName() {
		return "com.em.validation.ValidationTest";
	}

	@Override
	public IReflectorFactory getReflectorFactory() {
		return ReflectorFactory.INSTANCE;
	}
	
}
