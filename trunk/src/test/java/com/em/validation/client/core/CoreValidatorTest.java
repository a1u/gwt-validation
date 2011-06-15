package com.em.validation.client.core;

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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.client.model.cyclic.Cycle;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.model.tests.ITestCase;

public class CoreValidatorTest extends GwtValidationBaseTestCase{

	
	public static void testCyclicValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Cycle a = new Cycle();
		Cycle b = new Cycle();
		
		a.setOther(b);
		b.setOther(a);
		
		//validated cyclic object
		Set<ConstraintViolation<Cycle>> violations = validator.validate(a);
		
		assertEquals(3, violations.size());
	}
	
	public static void testRecurisveValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Cycle recursive = new Cycle();
		recursive.setOther(recursive);
		
		//validated cyclic object
		Set<ConstraintViolation<Cycle>> violations = validator.validate(recursive);
		
		assertEquals(2, violations.size());
	}
	
}
