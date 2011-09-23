package com.em.validation.client.core;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class CoreMetadataTest {

	public static void testGroupFinderOnClass(ITestCase testCase) {

		//get bean descriptor and initial finder
		IReflector groupTestReflector = ReflectorFactory.INSTANCE.getReflector(GroupTestClass.class);
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class);
		ConstraintFinder finder = descriptor.findConstraints();
		
		//test group parent
		testCase.localAssertEquals("parentSizeConstrainedString",groupTestReflector.getValue("parentSizeConstrainedString", new GroupTestClass()));
		
		//test with no groups
		testCase.localAssertEquals(5, finder.getConstraintDescriptors().size());
		testCase.localAssertTrue(finder.hasConstraints());

		//test with the MaxGroup class alone
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(MaxGroup.class);
		finder.lookingAt(Scope.HIERARCHY);
		testCase.localAssertEquals(5, finder.getConstraintDescriptors().size());
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		testCase.localAssertEquals(4, finder.getConstraintDescriptors().size());
		
		//add Default.class (INCLUDING EXTENSIONS)
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(Default.class);
		testCase.localAssertEquals(5, finder.getConstraintDescriptors().size());
		
		//add MaxGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class);
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		testCase.localAssertEquals(3, finder.getConstraintDescriptors().size());

		//add ExtendedGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class,ExtendedGroup.class);
		testCase.localAssertEquals(2, finder.getConstraintDescriptors().size());
		
		//add BasicGroup.class
		finder.unorderedAndMatchingGroups(Default.class,MaxGroup.class,ExtendedGroup.class,BasicGroup.class);
		testCase.localAssertEquals(1, finder.getConstraintDescriptors().size());
		
		//new finder, only max group
		testCase.localAssertEquals(5, descriptor.findConstraints().unorderedAndMatchingGroups(MaxGroup.class).getConstraintDescriptors().size());
		
		//new finder, only extended
		testCase.localAssertEquals(3, descriptor.findConstraints().unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
		//new finder, looking at local (element) scope
		finder = descriptor.findConstraints().lookingAt(Scope.LOCAL_ELEMENT);
		testCase.localAssertEquals(4, finder.getConstraintDescriptors().size());
	}
	
	public static void testGroupFinderOnProperty(ITestCase testCase) {
		//get property descriptor and initial finder
		PropertyDescriptor propertyDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class).getConstraintsForProperty("testString");
		ConstraintFinder finder = propertyDescriptor.findConstraints();
		
		//all constraints
		testCase.localAssertEquals(2, finder.getConstraintDescriptors().size());
		
		//only the extended group
		testCase.localAssertEquals(2, finder.unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());		
	}
	
	public static void testDeclaredOnFinder(ITestCase testCase) {
		
		//get bean descriptor for generic test class
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(testCase.getReflectorFactory().getReflector(TestClass.class));
		
		//get finder
		ConstraintFinder finder = descriptor.findConstraints().declaredOn(ElementType.METHOD);
				
		//assert that the finder object is not null
		testCase.localAssertNotNull(finder);
		
		//check local and hierarchy scope for the proper number of constraints
		testCase.localAssertEquals(10, finder.lookingAt(Scope.HIERARCHY).getConstraintDescriptors().size());
		testCase.localAssertEquals(5, finder.lookingAt(Scope.LOCAL_ELEMENT).getConstraintDescriptors().size());
		
		//change to FIELDs in the LOCAL_ELEMENT scope
		finder.lookingAt(Scope.LOCAL_ELEMENT).declaredOn(ElementType.FIELD);
		testCase.localAssertEquals(6, finder.getConstraintDescriptors().size());
	}
	
	public static void testBeanDescriptor(ITestCase testCase) {

		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(testCase.getReflectorFactory().getReflector(TestClass.class));
				
		testCase.localAssertEquals(TestClass.class, descriptor.getElementClass());
	}
	
}
