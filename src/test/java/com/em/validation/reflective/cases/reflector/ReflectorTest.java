package com.em.validation.reflective.cases.reflector;

import org.junit.Test;

import com.em.validation.client.core.reflector.CoreReflectorTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class ReflectorTest extends ReflectiveValidationBaseTestClass {

	@Test
	public void testDeepReflectionChain() {
		CoreReflectorTest.testDeepReflectionChain(this);
	}
	
	@Test
	public void testAnonymousInterfaceImplementation() {
		CoreReflectorTest.testAnonymousInterfaceImplementation(this);
	}
	
	@Test
	public void testClassWithNoConstraintsButConstrainedInterfaces() {
		CoreReflectorTest.testClassWithNoConstraintsButConstrainedInterfaces(this);
	}
	
}
