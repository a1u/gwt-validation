package com.em.validation.client.cases.reflector;

import com.em.validation.client.core.reflector.CoreReflectorTest;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class ReflectorTest extends GwtValidationBaseTestCase {

	public void testDeepReflectionChain() {
		CoreReflectorTest.testDeepReflectionChain(this);
	}
	
	public void testAnonymousInterfaceImplementation() {
		CoreReflectorTest.testAnonymousInterfaceImplementation(this);
	}
	
	public void testClassWithNoConstraintsButConstrainedInterfaces() {
		CoreReflectorTest.testClassWithNoConstraintsButConstrainedInterfaces(this);
	}
	
}
