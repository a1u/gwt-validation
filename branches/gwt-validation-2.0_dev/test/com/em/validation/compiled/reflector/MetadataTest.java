package com.em.validation.compiled.reflector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.BeforeClass;
import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.compiler.TestCompiler;
import com.em.validation.rebind.generator.source.ReflectorGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;

public class MetadataTest {

	private static BeanDescriptor descriptor = null;
	
	@BeforeClass
	public static void getBeanDescriptor() throws InstantiationException, IllegalAccessException {
		GroupTestClass groupTest = new GroupTestClass();
		
		ClassDescriptor reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(groupTest.getClass());
		
		Class<?> reflectorClass = TestCompiler.INSTANCE.loadClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<GroupTestClass> reflector = (IReflector<GroupTestClass>) reflectorClass.newInstance(); 

		//get descriptor and finder
		MetadataTest.descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
	}
	
	@Test
	public void testGroupFinderOnClass() {
		//get bean descriptor and initial finder
		BeanDescriptor descriptor = MetadataTest.descriptor;
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
	public void testGroupFinderOnProperty() {
		//get property descriptor and initial finder
		PropertyDescriptor propertyDescriptor = MetadataTest.descriptor.getConstraintsForProperty("testString");
		ConstraintFinder finder = propertyDescriptor.findConstraints();
		
		//all constraints
		assertEquals(3, finder.getConstraintDescriptors().size());
		
		//only the extended group
		assertEquals(2, finder.unorderedAndMatchingGroups(ExtendedGroup.class).getConstraintDescriptors().size());
		
	}
}
