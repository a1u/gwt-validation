package com.em.validation.client.core.defects;

import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.defects.defect_005.StrangeCapitalization;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreDefect_005 extends GwtValidationBaseTestCase {

	@Test
	public static void testStrangeCapitalization(IReflectorFactory factory) {
		StrangeCapitalization capitalization = new StrangeCapitalization();	
		
		IReflector<StrangeCapitalization> reflector = factory.getReflector(capitalization.getClass()); 
		
		//no null reflectors
		assertNotNull(reflector);
		
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(reflector);
		
		//check reflector
		assertEquals("uri", reflector.getValue("URIs", capitalization));
		assertEquals("nonstandard", reflector.getValue("nOnStAnDarD", capitalization));
		assertEquals("alllowercase", reflector.getValue("alllowercase", capitalization));
		assertEquals("alluppercase", reflector.getValue("ALLUPPERCASE", capitalization));		
		
		//check descriptor
		assertEquals(2,descriptor.getConstraintsForProperty("nOnStAnDarD").getConstraintDescriptors().size());		
	}
	
}
