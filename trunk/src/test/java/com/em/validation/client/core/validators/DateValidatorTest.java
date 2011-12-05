package com.em.validation.client.core.validators;

import java.util.Date;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class DateValidatorTest extends GwtValidationBaseTestCase {

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

	@Test
	public void testFutureValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		ExampleDateValidatorTest dvt = new ExampleDateValidatorTest();

		// set past date
		dvt.setInTheFuture(new Date(0));
		// get validation error
		Assert.assertEquals(1, validator.validate(dvt).size());
		// set in the future
		Date now = new Date();
		dvt.setInTheFuture(new Date(now.getTime() + 100000));
		Assert.assertEquals(0, validator.validate(dvt).size());
	}

	@Test
	public void testPastValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		ExampleDateValidatorTest dvt = new ExampleDateValidatorTest();

		// set future date
		Date now = new Date();
		dvt.setInThePast(new Date(now.getTime() + 100000));
		// get validation error
		Assert.assertEquals(1, validator.validate(dvt).size());

		// set past date
		dvt.setInThePast(new Date(0));
		Assert.assertEquals(0, validator.validate(dvt).size());
	}

}
