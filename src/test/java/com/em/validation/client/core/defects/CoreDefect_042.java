package com.em.validation.client.core.defects;

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