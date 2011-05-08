package com.em.validation.compiled.reflector;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.Payload;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.composed.ComposedConstraint;
import com.em.validation.client.model.composed.ComposedSingleViolationConstraint;
import com.em.validation.client.model.composed.ComposedTestClass;
import com.em.validation.client.model.generic.ExtendedInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ConstraintDescriptionGenerator;
import com.em.validation.rebind.generator.source.ReflectorGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;

public class ReflectorGenerationTest {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		Size annotation = new Size(){

			@Override
			public Class<? extends Annotation> annotationType() {
				return Size.class;
			}

			@Override
			public Class<?>[] groups() {
				return new Class<?>[]{Default.class};
			}

			@Override
			public String message() {
				return null;
			}

			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}

			@Override
			public int max() {
				return 412;
			}

			@Override
			public int min() {
				return -12;
			}
			
		};
		
		ClassDescriptor descriptor = ConstraintDescriptionGenerator.INSTANCE.generateConstraintDescriptor(annotation);
		Class<?> descriptorClass = TestCompiler.INSTANCE.loadClass(descriptor);
		
		@SuppressWarnings("unchecked")
		ConstraintDescriptor<Size> constraintDescriptor = (ConstraintDescriptor<Size>)descriptorClass.newInstance();
		
		//assertions
		assertEquals(Size.class, constraintDescriptor.getAnnotation().annotationType());
		assertEquals(annotation.max(), constraintDescriptor.getAnnotation().max());
		assertEquals(annotation.min(), constraintDescriptor.getAnnotation().min());
		assertEquals(annotation.groups()[0], constraintDescriptor.getGroups().toArray(new Class<?>[]{})[0]);
	}
	
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
		
		assertEquals(2, constraints.size());
		
		for(ConstraintDescriptor<?> descriptor : constraints) {
			if(ComposedConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				
			} else if(ComposedSingleViolationConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				assertTrue(descriptor.isReportAsSingleViolation());
			}
		}
		
	}
}
