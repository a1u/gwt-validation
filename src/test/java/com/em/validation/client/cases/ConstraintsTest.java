package com.em.validation.client.cases;

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

import javax.validation.metadata.BeanDescriptor;

import com.em.validation.client.core.CoreConstraintsTest;
import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.overlay.OverlayClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;

public class ConstraintsTest extends GwtValidationBaseTestCase {
	
	public void testConstraintGeneration() {
		CoreConstraintsTest.testConstraintGeneration(this.getReflectorFactory());
	}
	
	public void testComposedConstraints() {
		CoreConstraintsTest.testComposedConstraints(this.getReflectorFactory());
	}
	
	public void testOverridesConstraints() {
		CoreConstraintsTest.testOverridesConstraints(this.getReflectorFactory());
	}
	
	/**
	 * Only tested in gwt/generated/deferred binding mode.  overlay types cannot be handled by reflective java.
	 * 
	 */
	public void testOverlayType() {
		
		//create instance
		OverlayClass overlay = new OverlayClass();
		
		//get descriptor
		BeanDescriptor descriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(OverlayClass.class);
		
		//get reflector
		IReflector<OverlayClass> overlayReflector = this.getReflectorFactory().getReflector(OverlayClass.class);
		
		//test reflection
		assertEquals(overlay.getTestString(), overlayReflector.getValue("testString",overlay));
		
		//set constraints
		assertEquals(1,descriptor.getConstrainedProperties().size());
		assertEquals(2,descriptor.getConstraintsForProperty("testString").getConstraintDescriptors().size());
	}
	
	public void testCascadedConstraints() {
		CoreConstraintsTest.testCascadedConstraints(this.getReflectorFactory());
	}
	
	public void testReflectorGroupSequence() {
		CoreConstraintsTest.testReflectorGroupSequence(this);
	}
}
