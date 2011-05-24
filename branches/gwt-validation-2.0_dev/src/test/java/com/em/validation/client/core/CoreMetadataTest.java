package com.em.validation.client.core;

/*
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

import java.lang.annotation.ElementType;

import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.metadata.Scope;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;

public class CoreMetadataTest extends GwtValidationBaseTestCase {

	public static void testGroupFinderOnClass(IReflectorFactory factory) {

		//get bean descriptor and initial finder
		IReflector<GroupTestClass> groupTestReflector = ReflectorFactory.INSTANCE.getReflector(GroupTestClass.class);
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class);
		ConstraintFinder finder = descriptor.findConstraints();
		
		//test group parent
		assertEquals("parentSizeConstrainedString",groupTestReflector.getValue("parentSizeConstrainedString", new GroupTestClass()));
		
		//test with no groups
		assertEquals(8, finder.getConstraintDescriptors().size());
		assertTrue(finder.hasConstraints());

		//test with the MaxGroup class alone
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(MaxGroup.class);
		finder.lookingAt(Scope.HIERARCHY);
		assertEquals(5, finder.getConstraintDescriptors().size());
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		assertEquals(4, finder.getConstraintDescriptors().size());
		
		//add Default.class (INCLUDING EXTENSIONS)
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(Default.class);
		assertEquals(5, finder.getConstraintDescriptors().size());
		
		//add MaxGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class);
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		assertEquals(3, finder.getConstraintDescriptors().size());

		//add ExtendedGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class,ExtendedGroup.class);
		assertEquals(2, finder.getConstraintDescriptors().size());
		
		//add BasicGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class,ExtendedGroup.class,BasicGroup.class);
		assertEquals(1, finder.getConstraintDescriptors().size());
		
		//new finder, only max group
		assertEquals(5, descriptor.findConstraints().unorderedAndMatchingGroups(MaxGroup.class).getConstraintDescriptors().size());
		
		//new finder, only extended
		assertEquals(3, descriptor.findConstraints().unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
		//new finder, looking at local (element) scope
		finder = descriptor.findConstraints()
				.lookingAt(Scope.LOCAL_ELEMENT);
		assertEquals(6, finder.getConstraintDescriptors().size());
	}
	
	public static void testGroupFinderOnProperty(IReflectorFactory factory) {
		//get property descriptor and initial finder
		PropertyDescriptor propertyDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class).getConstraintsForProperty("testString");
		ConstraintFinder finder = propertyDescriptor.findConstraints();
		
		//all constraints
		assertEquals(3, finder.getConstraintDescriptors().size());
		
		//only the extended group
		assertEquals(2, finder.unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
	}
	
	public static void testDeclaredOnFinder(IReflectorFactory factory) {
		
		//get bean descriptor for generic test class
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(factory.getReflector(TestClass.class));
		
		//get finder
		ConstraintFinder finder = descriptor.findConstraints().lookingAt(Scope.LOCAL_ELEMENT).declaredOn(ElementType.METHOD);
				
		//assert that the finder object is not null
		assertNotNull(finder);
		
		//there are only 3 items declared on a method in that class
		assertEquals(5, finder.getConstraintDescriptors().size());
		
		//change to Scope.HIERARCHY
		finder.lookingAt(Scope.HIERARCHY);
		assertEquals(12, finder.getConstraintDescriptors().size());
		
		//change to FIELDs in the LOCAL_ELEMENT scope
		finder.lookingAt(Scope.LOCAL_ELEMENT).declaredOn(ElementType.FIELD);
		assertEquals(5, finder.getConstraintDescriptors().size());
	}
	
}
