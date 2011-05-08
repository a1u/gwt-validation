package com.em.validation.client.reflector.defects;

import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;

import com.em.validation.client.GwtValidationBaseTestCase;
import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.defects.defect_005.StrangeCapitalization;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class Defect_005 extends GwtValidationBaseTestCase {

	@Test
	public void testStrangeCapitalization() {
		StrangeCapitalization capitalization = new StrangeCapitalization();	
		
		IReflector<StrangeCapitalization> reflector = ReflectorFactory.INSTANCE.getReflector(capitalization.getClass()); 
		
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
