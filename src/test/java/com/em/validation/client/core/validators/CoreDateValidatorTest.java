package com.em.validation.client.core.validators;

import java.util.Date;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import com.em.validation.client.model.tests.ITestCase;

public class CoreDateValidatorTest {

	public static class ExampleDateValidatorTest {
		
		@Past
		private Date inThePast = null;
		
		@Future
		private Date inTheFuture = null;

		public Date getInThePast() {
			return inThePast;
		}

		public void setInThePast(Date inThePast) {
			this.inThePast = inThePast;
		}

		public Date getInTheFuture() {
			return inTheFuture;
		}

		public void setInTheFuture(Date inTheFuture) {
			this.inTheFuture = inTheFuture;
		}	
		
	}
	
	public static void testFutureValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		ExampleDateValidatorTest dvt = new ExampleDateValidatorTest();
		
		//set past date
		dvt.setInTheFuture(new Date(0));
		//get validation error
		testCase.localAssertEquals(1, validator.validate(dvt).size());
		//set in the future
		Date now = new Date();
		dvt.setInTheFuture(new Date(now.getTime() + 100000));
		testCase.localAssertEquals(0, validator.validate(dvt).size());
	}
	
	public static void testPastValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		ExampleDateValidatorTest dvt = new ExampleDateValidatorTest();

		//set future date 
		Date now = new Date();
		dvt.setInThePast(new Date(now.getTime() + 100000));
		//get validation error
		testCase.localAssertEquals(1, validator.validate(dvt).size());
		
		//set past date
		dvt.setInThePast(new Date(0));
		testCase.localAssertEquals(0, validator.validate(dvt).size());
	}
	
}
