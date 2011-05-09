package com.em.validation.compiled.model.test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ReflectorFactoryGenerator;

public class CompiledValidationBaseTestClass implements ITestCase {

	private static IReflectorFactory factory = null;
	
	public CompiledValidationBaseTestClass() {
		DescriptorFactory.INSTANCE.setReflectorFactory(this.getReflectorFactory());
	}
	
	@Override
	public IReflectorFactory getReflectorFactory() {
		if(factory == null) {
			Class<?> factoryClass = TestCompiler.INSTANCE.loadClass(ReflectorFactoryGenerator.INSTANCE.getReflectorFactoryDescriptors());
			try {
				CompiledValidationBaseTestClass.factory = (IReflectorFactory)factoryClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
		return CompiledValidationBaseTestClass.factory;
	}	
	
}
