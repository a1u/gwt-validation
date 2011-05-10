package com.em.validation.reflective.model.test;

import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;

public class ReflectiveValidationBaseTestClass implements ITestCase {

	@Override
	public IReflectorFactory getReflectorFactory() {
		return ReflectorFactory.INSTANCE;
	}
	
}
