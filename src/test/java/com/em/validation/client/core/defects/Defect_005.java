package com.em.validation.client.core.defects;

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

import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.defects.defect_005.StrangeCapitalization;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;

public class Defect_005 extends GwtValidationBaseTestCase {

	@Test
	public void testStrangeCapitalization() {
		StrangeCapitalization capitalization = new StrangeCapitalization();	
		
		IReflector reflector = this.getReflectorFactory().getReflector(capitalization.getClass()); 
		
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
