package com.em.validation.client.core;

import java.util.Set;

import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.composed.ComposedConstraint;
import com.em.validation.client.model.composed.ComposedSingleViolationConstraint;
import com.em.validation.client.model.composed.ComposedTestClass;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart1;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart2;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreConstraintsTest extends GwtValidationBaseTestCase {
	
	public static void testConstraintGeneration(IReflectorFactory factory) {
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
	
	public static void testComposedConstraints(IReflectorFactory factory) {
		//test composed constraint
		BeanDescriptor beanDesc = DescriptorFactory.INSTANCE.getBeanDescriptor(ComposedTestClass.class);
		
		Set<ConstraintDescriptor<?>> constraints = beanDesc.getConstraintDescriptors();
		
		assertEquals(4, constraints.size());
		
		for(ConstraintDescriptor<?> descriptor : constraints) {
			if(ComposedConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				assertEquals(2, descriptor.getComposingConstraints().size());
			} else if(ComposedSingleViolationConstraint.class.equals(descriptor.getAnnotation().annotationType())){
				assertTrue(descriptor.isReportAsSingleViolation());
				assertEquals(3, descriptor.getComposingConstraints().size());
			}else if(CyclicalComposedConstraintPart1.class.equals(descriptor.getAnnotation().annotationType())) {
				assertEquals(2, descriptor.getComposingConstraints().size());
			} else if(CyclicalComposedConstraintPart2.class.equals(descriptor.getAnnotation().annotationType())) {
				assertEquals(1, descriptor.getComposingConstraints().size());
				assertTrue(descriptor.isReportAsSingleViolation());
			}
		}
		
	}

}
