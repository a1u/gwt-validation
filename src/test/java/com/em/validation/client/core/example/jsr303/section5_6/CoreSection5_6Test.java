package com.em.validation.client.core.example.jsr303.section5_6;

/*
Based on work in the JSR-303 for provable conformity to the standard
 
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.example.jsr303.section5_6.constraint.NotEmpty;
import com.em.validation.client.model.example.jsr303.section5_6.model.Book;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreSection5_6Test extends GwtValidationBaseTestCase {

	public static void testConstraintMetadataSectionTest(IReflectorFactory factory) {
	
		//get descriptor
		BeanDescriptor bookDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(Book.class);
				
		assertFalse(bookDescriptor.hasConstraints());
		assertTrue(bookDescriptor.isBeanConstrained());
		assertEquals("no bean-level constraint",0,bookDescriptor.getConstraintDescriptors().size());
		
		//"author" and "title"
		assertEquals(2,bookDescriptor.getConstrainedProperties().size());
		
		//not a property
		assertNull(bookDescriptor.getConstraintsForProperty("doesNotExist"));

		//property with no constraint
		assertNull(bookDescriptor.getConstraintsForProperty("description"));
		
		//jsr303 example calls for "ElementDescriptor" here but then uses "getPropertyName()" which is not a method of ElementDescriptor...
		PropertyDescriptor propertyDescriptor = bookDescriptor.getConstraintsForProperty("title");
		assertEquals(2, propertyDescriptor.getConstraintDescriptors().size());
		assertEquals("title",propertyDescriptor.getPropertyName());

		//assertions on the constraint descriptors
		for(ConstraintDescriptor<?> constraintDescriptor : propertyDescriptor.getConstraintDescriptors()) {
			if(NotEmpty.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				//assertions on NotEmpty
				assertEquals(NotEmpty.class,constraintDescriptor.getAnnotation().annotationType());
				assertEquals(2, constraintDescriptor.getGroups().size());
				assertEquals(2, constraintDescriptor.getComposingConstraints().size());
				assertTrue(constraintDescriptor.isReportAsSingleViolation());
				
				//not empty must have a not null constraint
				boolean notNullPresence = false;
				for(ConstraintDescriptor<?> composingDescriptor : constraintDescriptor.getComposingConstraints()) {
					if(NotNull.class.equals(composingDescriptor.getAnnotation().annotationType())) {
						notNullPresence = true;
					}
				}
				assertTrue(notNullPresence);				
			} else if(Size.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				assertEquals(Size.class,constraintDescriptor.getAnnotation().annotationType());
				assertEquals(30,constraintDescriptor.getAttributes().get("max"));
				assertEquals(1,constraintDescriptor.getGroups().size());
				//get author property descriptor
				propertyDescriptor = bookDescriptor.getConstraintsForProperty("author");
				assertEquals(1,propertyDescriptor.getConstraintDescriptors().size());
				assertTrue(propertyDescriptor.isCascaded());
			} else {
				assertTrue("There should only be two constraints.",false);
			}
		}
		

	}
	
	
}
