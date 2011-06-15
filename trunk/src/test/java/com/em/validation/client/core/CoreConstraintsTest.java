package com.em.validation.client.core;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.cascade.Inside;
import com.em.validation.client.model.cascade.Outside;
import com.em.validation.client.model.composed.ComposedConstraint;
import com.em.validation.client.model.composed.ComposedSingleViolationConstraint;
import com.em.validation.client.model.composed.ComposedTestClass;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart1;
import com.em.validation.client.model.composed.CyclicalComposedConstraintPart2;
import com.em.validation.client.model.constraint.TestZipCode;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.override.ZipCodeContainer;
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
		
		//test interface
		assertEquals("testInterfaceString", testClassReflector.getValue("testInterfaceString", testClassInstance));
		assertEquals("parentAbstractString", testClassReflector.getValue("parentAbstractString", testClassInstance));
		
		//test parent values
		assertEquals("publicParentString", testClassReflector.getValue("publicParentString", testClassInstance));
	}
	
	public static void testComposedConstraints(IReflectorFactory factory) {
		//test composed constraint
		BeanDescriptor beanDesc = DescriptorFactory.INSTANCE.getBeanDescriptor(ComposedTestClass.class);
		
		Set<PropertyDescriptor> properties = beanDesc.getConstrainedProperties();
		Set<ConstraintDescriptor<?>> constraints = new HashSet<ConstraintDescriptor<?>>();
		for(PropertyDescriptor prop : properties) {
			constraints.addAll(prop.getConstraintDescriptors());
		}
		
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
	
	public static void testOverridesConstraints(IReflectorFactory factory) {
		
		BeanDescriptor beanDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(ZipCodeContainer.class);		
		
		Set<ConstraintDescriptor<?>> descriptors = beanDescriptor.getConstraintsForProperty("zipCode").getConstraintDescriptors();
		
		assertTrue(descriptors.size() > 0);
		
		//get the parent constraint
		ConstraintDescriptor<?> parent = descriptors.iterator().next();
		assertEquals(TestZipCode.class, parent.getAnnotation().annotationType());
		
		//get the composed constraints
		Set<ConstraintDescriptor<?>> composedOf = parent.getComposingConstraints();
		
		//assert that there are two composing constraints for the ZipCodeExample
		assertEquals(5,composedOf.size());					

		//go through the list and apply tests
		for(ConstraintDescriptor<?> composer : composedOf) {
			if(Size.class.equals(composer.getAnnotation().annotationType())) {
				Size s = (Size)composer.getAnnotation();
				if("TEST1".equals(s.message())) {
					assertEquals(400, s.max());
					assertEquals(12, s.min());
				} else if("TEST2".equals(s.message())) {
					assertEquals(-400, s.min());
					assertEquals(12, s.max());
				} else if("TEST3".equals(s.message())) {
					assertEquals(22, s.min());
					assertEquals(22, s.max());
				} else if("TEST4".equals(s.message())) {
					assertEquals(6, s.min());
					assertEquals(6, s.max());
				}
			} else if(Pattern.class.equals(composer.getAnnotation().annotationType())) {
				Pattern p = (Pattern)composer.getAnnotation();
				assertEquals("[0-8]*", p.regexp());
			}			
		}
	}
	
	public static void testCascadedConstraints(IReflectorFactory factory) {
		
		Outside outside = new Outside();
		
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(outside);
		
		assertTrue(descriptor.isBeanConstrained());
		assertEquals(2,descriptor.getConstrainedProperties().size());
		
		//check cascade
		PropertyDescriptor propDescriptor = descriptor.getConstraintsForProperty("inside");
		assertNotNull(propDescriptor);
		assertEquals(1, propDescriptor.getConstraintDescriptors().size());
		assertEquals(Inside.class,propDescriptor.getElementClass());
		assertTrue("Property descriptor should be cascaded, actual value: " + propDescriptor.isCascaded(), propDescriptor.isCascaded());
		
		//check non-cascade
		PropertyDescriptor noCascadeDescriptor = descriptor.getConstraintsForProperty("noCascade");
		assertNotNull(noCascadeDescriptor);
		assertEquals(1, noCascadeDescriptor.getConstraintDescriptors().size());
		assertFalse("Property descriptor should NOT be cascaded, actual value: " + noCascadeDescriptor.isCascaded(), noCascadeDescriptor.isCascaded());
				
	}

}
