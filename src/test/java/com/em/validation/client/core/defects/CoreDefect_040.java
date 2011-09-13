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

import com.em.validation.client.model.defects.defect_040.Person_040;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.reflector.IReflector;

public class CoreDefect_040 {

	public static void testBehaviorOfIssue_040(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		try {
			Person_040 testPerson = new Person_040();
		
			Set<ConstraintViolation<Person_040>> violations = validator.validate(testPerson);
			testCase.localAssertEquals(1, violations.size());
			
			//get the class descriptor
			BeanDescriptor descriptor = validator.getConstraintsForClass(Person_040.class);
			
			//prove that the property descriptor is simply null
			PropertyDescriptor prop = descriptor.getConstraintsForProperty("upperCaseName");
			testCase.localAssertTrue(prop == null);
			
			//get a reflector
			IReflector personReflector = testCase.getReflectorFactory().getReflector(Person_040.class);
			
			//prove property name exists
			testCase.localAssertTrue(personReflector.getPropertyNames().contains("upperCaseName"));
			testCase.localAssertFalse(personReflector.getPropertyNames().contains("getUpperCaseName"));
			testCase.localAssertFalse(personReflector.getPropertyNames().contains("UpperCaseName"));
			
			//prove property is available by reflection
			testPerson.setName("pops");
			testCase.localAssertEquals("pops should be POPS with uppercase", "POPS", testPerson.getUpperCaseName());
			testCase.localAssertEquals("pops should be POPS with uppercase", "POPS", personReflector.getValue("upperCaseName", testPerson));
		} catch (Exception ex) {
			ex.printStackTrace();
			testCase.localFail("Should not throw exception during validation.");
		}
	}
	
}