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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_040.Person_040;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;

public class Defect_040 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_040() {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		try {
			Person_040 testPerson = new Person_040();
		
			Set<ConstraintViolation<Person_040>> violations = validator.validate(testPerson);
			Assert.assertEquals(1, violations.size());
			
			//get the class descriptor
			BeanDescriptor descriptor = validator.getConstraintsForClass(Person_040.class);
			
			//prove that the property descriptor is simply null
			PropertyDescriptor prop = descriptor.getConstraintsForProperty("upperCaseName");
			Assert.assertTrue(prop == null);
			
			//get a reflector
			IReflector personReflector = this.getReflectorFactory().getReflector(Person_040.class);
			
			//prove property name exists
			Assert.assertTrue(personReflector.getPropertyNames().contains("upperCaseName"));
			Assert.assertFalse(personReflector.getPropertyNames().contains("getUpperCaseName"));
			Assert.assertFalse(personReflector.getPropertyNames().contains("UpperCaseName"));
			
			//prove property is available by reflection
			testPerson.setName("pops");
			Assert.assertEquals("pops should be POPS with uppercase", "POPS", testPerson.getUpperCaseName());
			Assert.assertEquals("pops should be POPS with uppercase", "POPS", personReflector.getValue("upperCaseName", testPerson));
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Should not throw exception during validation.");
		}
	}
	
}