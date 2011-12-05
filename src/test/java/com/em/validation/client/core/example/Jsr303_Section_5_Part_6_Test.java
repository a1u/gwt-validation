package com.em.validation.client.core.example;

/*
Based on work in the JSR-303 for provable conformity to the standard
 
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.constraints.NotEmpty;
import com.em.validation.client.metadata.factory.DescriptorFactory;
import com.em.validation.client.model.example.Book;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Jsr303_Section_5_Part_6_Test extends GwtValidationBaseTestCase {

	@Test
	public void testConstraintMetadataSectionTest() {
	
		//get descriptor
		BeanDescriptor bookDescriptor = DescriptorFactory.INSTANCE.getBeanDescriptor(Book.class);
				
		Assert.assertFalse(bookDescriptor.hasConstraints());
		Assert.assertTrue(bookDescriptor.isBeanConstrained());
		Assert.assertEquals("no bean-level constraint",0,bookDescriptor.getConstraintDescriptors().size());
		
		//"author" and "title"
		Assert.assertEquals(2,bookDescriptor.getConstrainedProperties().size());
		
		//not a property
		Assert.assertNull(bookDescriptor.getConstraintsForProperty("doesNotExist"));

		//property with no constraint
		Assert.assertNull(bookDescriptor.getConstraintsForProperty("description"));
		
		//jsr303 example calls for "ElementDescriptor" here but then uses "getPropertyName()" which is not a method of ElementDescriptor...
		PropertyDescriptor propertyDescriptor = bookDescriptor.getConstraintsForProperty("title");
		Assert.assertEquals(2, propertyDescriptor.getConstraintDescriptors().size());
		Assert.assertEquals("title",propertyDescriptor.getPropertyName());

		//Assert.assertions on the constraint descriptors
		for(ConstraintDescriptor<?> constraintDescriptor : propertyDescriptor.getConstraintDescriptors()) {
			if(NotEmpty.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				//Assert.assertions on NotEmpty
				Assert.assertEquals(NotEmpty.class,constraintDescriptor.getAnnotation().annotationType());
				Assert.assertEquals(2, constraintDescriptor.getGroups().size());
				Assert.assertEquals(2, constraintDescriptor.getComposingConstraints().size());
				Assert.assertTrue(constraintDescriptor.isReportAsSingleViolation());
				
				//not empty must have a not null constraint
				boolean notNullPresence = false;
				for(ConstraintDescriptor<?> composingDescriptor : constraintDescriptor.getComposingConstraints()) {
					if(NotNull.class.equals(composingDescriptor.getAnnotation().annotationType())) {
						notNullPresence = true;
					}
				}
				Assert.assertTrue(notNullPresence);				
			} else if(Size.class.equals(constraintDescriptor.getAnnotation().annotationType())) {
				Assert.assertEquals(Size.class,constraintDescriptor.getAnnotation().annotationType());
				Assert.assertEquals(30,constraintDescriptor.getAttributes().get("max"));
				Assert.assertEquals(1,constraintDescriptor.getGroups().size());
				//get author property descriptor
				propertyDescriptor = bookDescriptor.getConstraintsForProperty("author");
				Assert.assertEquals(1,propertyDescriptor.getConstraintDescriptors().size());
				Assert.assertTrue(propertyDescriptor.isCascaded());
			} else {
				Assert.assertTrue("There should only be two constraints.",false);
			}
		}
		

	}
	
	
}
