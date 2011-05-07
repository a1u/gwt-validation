package com.em.validation.client.reflector;

import com.em.validation.client.model.generic.TestClass;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectorGenerationTest extends GWTTestCase{

	@Override
	public String getModuleName() {
		return "com.em.validation.ValidationTest";
	}
	
	public void testConstraintGeneration() {
		//get the factory instance
		IReflectorFactory factory = ReflectorFactory.INSTANCE;

		//assert that we got a usable factory
		assertNotNull(factory);
		
		//test class
		TestClass testClassInstance = new TestClass();
		IReflector<TestClass> testClassReflector = factory.getReflector(TestClass.class);
		
		assertEquals(0, testClassReflector.getValue("testInt", testClassInstance));
		//set new value
		testClassInstance.setTestInt(430);
		assertEquals(430, testClassReflector.getValue("testInt", testClassInstance));	
		
	}

}
