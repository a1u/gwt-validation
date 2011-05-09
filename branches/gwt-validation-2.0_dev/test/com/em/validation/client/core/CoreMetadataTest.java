package com.em.validation.client.core;

import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreMetadataTest extends GwtValidationBaseTestCase {

	public static void testGroupFinderOnClass(IReflectorFactory factory) {

		//get bean descriptor and initial finder
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class);
		ConstraintFinder finder = descriptor.findConstraints();
		
		//test with no groups
		assertEquals(6, finder.getConstraintDescriptors().size());
		assertTrue(finder.hasConstraints());
		
		//add Default.class
		finder.unorderedAndMatchingGroups(Default.class);
		assertEquals(3, finder.getConstraintDescriptors().size());
		
		//add MaxGroup.class
		finder.unorderedAndMatchingGroups(MaxGroup.class);
		assertEquals(3, finder.getConstraintDescriptors().size());
		
		//add ExtendedGroup.class
		finder.unorderedAndMatchingGroups(ExtendedGroup.class);
		assertEquals(2, finder.getConstraintDescriptors().size());
		
		//add BasicGroup.class
		finder.unorderedAndMatchingGroups(BasicGroup.class);
		assertEquals(1, finder.getConstraintDescriptors().size());
		
		//new finder, only max group
		assertEquals(4, descriptor.findConstraints().unorderedAndMatchingGroups(MaxGroup.class).getConstraintDescriptors().size());
		
		//new finder, only extended
		assertEquals(3, descriptor.findConstraints().unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
		//new finder, showing chaining
		finder = descriptor.findConstraints()
					.unorderedAndMatchingGroups(Default.class)
					.unorderedAndMatchingGroups(BasicGroup.class);
		assertEquals(1, finder.getConstraintDescriptors().size());
	}
	
	@Test
	public static void testGroupFinderOnProperty(IReflectorFactory factory) {
		//get property descriptor and initial finder
		PropertyDescriptor propertyDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class).getConstraintsForProperty("testString");
		ConstraintFinder finder = propertyDescriptor.findConstraints();
		
		//all constraints
		assertEquals(3, finder.getConstraintDescriptors().size());
		
		//only the extended group
		assertEquals(2, finder.unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
	}
	
}
