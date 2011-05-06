package com.em.validation.compiled.reflector.factory;

import static junit.framework.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ReflectorFactoryGenerator;

public class ReflectorFactoryGeneratorTest {

	private static IReflectorFactory reflectorFactory = null;
	
	@BeforeClass
	public static void compileFactory() throws InstantiationException, IllegalAccessException {
		Class<?> factoryClass = TestCompiler.INSTANCE.loadClass(ReflectorFactoryGenerator.INSTANCE.getReflectorFactoryDescriptors());
		ReflectorFactoryGeneratorTest.reflectorFactory = (IReflectorFactory)factoryClass.newInstance();
	}
	
	@Test
	public void testGenerator() {
		IReflectorFactory factory = ReflectorFactoryGeneratorTest.reflectorFactory;
	
		TestClass testClassInstance = new TestClass();
		IReflector<TestClass> testClassReflector = factory.getReflector(TestClass.class);
		
		//check base value
		assertEquals(0, testClassReflector.getValue("testInt", testClassInstance));
		//set new value
		testClassInstance.setTestInt(430);
		assertEquals(430, testClassReflector.getValue("testInt", testClassInstance));		

	}
	
	
}
