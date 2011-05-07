package com.em.validation.compiled.reflector.defects;

import org.junit.Test;

import static org.junit.Assert.*;

import com.em.validation.client.model.defects.defect_024.TestPattern;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ReflectorFactoryGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;

public class Defect_024 {

	@Test
	public void testPatternEscapes() throws InstantiationException, IllegalAccessException {
		
		ClassDescriptor reflectorFactoryPackage = ReflectorFactoryGenerator.INSTANCE.getReflectorFactoryDescriptors();
		
		Class<?> factoryClass = TestCompiler.INSTANCE.loadClass(reflectorFactoryPackage);
		IReflectorFactory factory = (IReflectorFactory)factoryClass.newInstance();
		
		//get pattern reflector
		IReflector<TestPattern> testPatternReflector = factory.getReflector(TestPattern.class);
		
		//check out pattern 
		assertNotNull(testPatternReflector);
		
	}
	
}
