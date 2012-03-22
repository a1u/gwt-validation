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

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class MetadataTest extends GwtValidationBaseTestCase {

	@Test
	public void testGroupFinderOnClass() {

		// get bean descriptor and initial finder
		IReflector groupTestReflector = ReflectorFactory.INSTANCE.getReflector(GroupTestClass.class);
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class);
		ConstraintFinder finder = descriptor.findConstraints();

		// test group parent
		Assert.assertEquals("parentSizeConstrainedString", groupTestReflector.getValue("parentSizeConstrainedString", new GroupTestClass()));

		// test with no groups
		Assert.assertEquals(5, finder.getConstraintDescriptors().size());
		Assert.assertTrue(finder.hasConstraints());

		// test with the MaxGroup class alone
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(MaxGroup.class);
		finder.lookingAt(Scope.HIERARCHY);
		Assert.assertEquals(5, finder.getConstraintDescriptors().size());
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		Assert.assertEquals(4, finder.getConstraintDescriptors().size());

		// add Default.class (INCLUDING EXTENSIONS)
		finder = descriptor.findConstraints();
		finder.unorderedAndMatchingGroups(Default.class);
		Assert.assertEquals(5, finder.getConstraintDescriptors().size());

		// add MaxGroup.class
		finder.unorderedAndMatchingGroups(Default.class, MaxGroup.class);
		finder.lookingAt(Scope.LOCAL_ELEMENT);
		Assert.assertEquals(5, finder.getConstraintDescriptors().size());

		// add ExtendedGroup.class
		finder.unorderedAndMatchingGroups(Default.class, MaxGroup.class, ExtendedGroup.class);
		Assert.assertEquals(6, finder.getConstraintDescriptors().size());

		// add BasicGroup.class
		finder.unorderedAndMatchingGroups(Default.class, MaxGroup.class, ExtendedGroup.class, BasicGroup.class);
		Assert.assertEquals(6, finder.getConstraintDescriptors().size());

		// new finder, only max group
		Assert.assertEquals(5, descriptor.findConstraints().unorderedAndMatchingGroups(MaxGroup.class).getConstraintDescriptors().size());

		// new finder, only extended
		Assert.assertEquals(3, descriptor.findConstraints().unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());

		// new finder, looking at local (element) scope
		finder = descriptor.findConstraints().lookingAt(Scope.LOCAL_ELEMENT);
		Assert.assertEquals(4, finder.getConstraintDescriptors().size());
	}

	@Test
	public void testGroupFinderOnProperty() {
		// get property descriptor and initial finder
		PropertyDescriptor propertyDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(GroupTestClass.class).getConstraintsForProperty("testString");
		ConstraintFinder finder = propertyDescriptor.findConstraints();

		// all constraints
		Assert.assertEquals(2, finder.getConstraintDescriptors().size());

		// only the extended group
		Assert.assertEquals(2, finder.unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
	}

	@Test
	public void testDeclaredOnFinder() {

		// get bean descriptor for generic test class
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(this.getReflectorFactory().getReflector(TestClass.class));

		// get finder
		ConstraintFinder finder = descriptor.findConstraints().declaredOn(ElementType.METHOD);

		// assert that the finder object is not null
		Assert.assertNotNull(finder);

		// check local and hierarchy scope for the proper number of constraints
		Assert.assertEquals(10, finder.lookingAt(Scope.HIERARCHY).getConstraintDescriptors().size());
		Assert.assertEquals(5, finder.lookingAt(Scope.LOCAL_ELEMENT).getConstraintDescriptors().size());

		// change to FIELDs in the LOCAL_ELEMENT scope
		finder.lookingAt(Scope.LOCAL_ELEMENT).declaredOn(ElementType.FIELD);
		Assert.assertEquals(6, finder.getConstraintDescriptors().size());
	}

	@Test
	public void testBeanDescriptor() {

		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(this.getReflectorFactory().getReflector(TestClass.class));

		Assert.assertEquals(TestClass.class, descriptor.getElementClass());
	}

}
