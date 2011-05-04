package com.em.validation.rebind.reflector.gwt_1_0.defects;

import static org.junit.Assert.assertEquals;

import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;

import com.em.validation.client.model.defect_005.StrangeCapitalization;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.validation.metadata.factory.DescriptorFactory;
import com.em.validation.rebind.ReflectorGenerator;
import com.em.validation.rebind.compiler.TestCompiler;
import com.em.validation.rebind.metadata.ReflectorClassDescriptions;

public class Defect_005 {

	@Test
	public void testStrangeCapitalization() throws InstantiationException, IllegalAccessException {
		StrangeCapitalization capitalization = new StrangeCapitalization();
		
		ReflectorClassDescriptions reflectorPackage = ReflectorGenerator.INSTANCE.getReflectorDescirptions(capitalization.getClass());
		
		Class<?> reflectorClass = TestCompiler.loadReflectorClass(reflectorPackage);
		@SuppressWarnings("unchecked")
		IReflector<StrangeCapitalization> reflector = (IReflector<StrangeCapitalization>) reflectorClass.newInstance(); 
		
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
