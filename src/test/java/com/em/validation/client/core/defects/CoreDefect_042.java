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
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.em.validation.client.model.tests.ITestCase;

public class CoreDefect_042 {

	public static class Example_42_A {
		
		@Valid
		private Example_42_B b = new Example_42_B();
		
		@Valid
		@NotNull
		private Example_42_C c = new Example_42_C();

		public Example_42_B getB() {
			return b;
		}

		public void setB(Example_42_B b) {
			this.b = b;
		}

		public Example_42_C getC() {
			return c;
		}

		public void setC(Example_42_C c) {
			this.c = c;
		}		
	}
	
	public static class Example_42_B {
		
		@Size(max=5)
		private String name = "abcdefgh";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Example_42_C {

		@Size(max=5)
		private String name = "abcdefgh";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static void testBehaviorOfIssue_042(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Example_42_A a = new Example_42_A();
		
		Set<ConstraintViolation<Example_42_A>> violations = validator.validate(a);
		
		testCase.localAssertEquals(2, violations.size());		
	}
	
}