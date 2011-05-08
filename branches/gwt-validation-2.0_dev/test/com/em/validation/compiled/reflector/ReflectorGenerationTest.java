package com.em.validation.compiled.reflector;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.composed.ComposedConstraint;
import com.em.validation.client.model.composed.ComposedSingleViolationConstraint;
import com.em.validation.client.model.composed.ComposedTestClass;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart1;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart2;
import com.em.validation.client.model.generic.ExtendedInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ReflectorGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;

public class ReflectorGenerationTest {
	
	@Test
	public void testReflectorGeneration() throws InstantiationException, IllegalAccessException {
		
		TestClass testInstance = new TestClass();
		
		ClassDescriptor reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(testInstance.getClass());
		
		Class<?> reflectorClass = TestCompiler.INSTANCE.loadClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<TestClass> reflector = (IReflector<TestClass>) reflectorClass.newInstance(); 
		
		//check base value
		assertEquals(0, reflector.getValue("testInt", testInstance));
		
		//set new value
		testInstance.setTestInt(430);
		assertEquals(430, reflector.getValue("testInt", testInstance));		
	}
	
	@Test
	public void testDescriptors() throws InstantiationException, IllegalAccessException {
		TestClass testInstance = new TestClass();
		
		ClassDescriptor reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(testInstance.getClass());
		
		Class<?> reflectorClass = TestCompiler.INSTANCE.loadClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<TestClass> reflector = (IReflector<TestClass>) reflectorClass.newInstance(); 
		
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
		
		assertEquals(TestClass.class, descriptor.getElementClass());
	}
	
	@Test
	public void testInterfaceReflectorCreation() throws InstantiationException, IllegalAccessException {
		ClassDescriptor reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(ExtendedInterface.class);
		
		Class<?> reflectorClass = TestCompiler.INSTANCE.loadClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<ExtendedInterface> reflector = (IReflector<ExtendedInterface>) reflectorClass.newInstance(); 
		
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
		
		assertEquals(ExtendedInterface.class, descriptor.getElementClass());
		
		//get string prop from ExtendedInterface
		PropertyDescriptor stringDescriptor = descriptor.getConstraintsForProperty("string");
		assertNotNull(stringDescriptor);
		
		//should contain two properties
		assertEquals(2, stringDescriptor.getConstraintDescriptors().size());
		
		//now to test reflector aspect
		ExtendedInterface extension = new ExtendedInterface() {
			@Override
			public String getTestInterfaceString() {
				return "test interface string";
			}
			
			@Override
			public boolean isTrue() {
				return true;
			}
			
			@Override
			public boolean isFalse() {
				return false;
			}
			
			@Override
			public String getString() {
				return "test string";
			}
		};
		
		assertEquals(true, reflector.getValue("true", extension));
		assertEquals(false, reflector.getValue("false", extension));
		assertEquals("test string", reflector.getValue("string", extension));
		assertEquals("test interface string", reflector.getValue("testInterfaceString", extension));
		
	}
	
	@Test
	public void testComposedConstraints() throws InstantiationException, IllegalAccessException {
		ComposedTestClass groupTest = new ComposedTestClass();
		
		ClassDescriptor reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(groupTest.getClass());
		
		Class<?> reflectorClass = TestCompiler.INSTANCE.loadClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<ComposedTestClass> reflector = (IReflector<ComposedTestClass>) reflectorClass.newInstance(); 
		
		//test composed constraint
		BeanDescriptor beanDesc = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
		
		Set<ConstraintDescriptor<?>> constraints = beanDesc.getConstraintDescriptors();
		
		assertEquals(4, constraints.size());
		
		for(ConstraintDescriptor<?> descriptor : constraints) {
			if(ComposedConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				assertEquals(2, descriptor.getComposingConstraints().size());
			} else if(ComposedSingleViolationConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				assertTrue(descriptor.isReportAsSingleViolation());
				assertEquals(3, descriptor.getComposingConstraints().size());
			} else if(CyclicalComposedConstraintPart1.class.equals(descriptor.getAnnotation().annotationType())) {
				assertEquals(2, descriptor.getComposingConstraints().size());
				assertTrue(!descriptor.isReportAsSingleViolation());
			} else if(CyclicalComposedConstraintPart2.class.equals(descriptor.getAnnotation().annotationType())) {
				assertEquals(1, descriptor.getComposingConstraints().size());
				assertTrue(descriptor.isReportAsSingleViolation());
			}
		}
		
	}
}
