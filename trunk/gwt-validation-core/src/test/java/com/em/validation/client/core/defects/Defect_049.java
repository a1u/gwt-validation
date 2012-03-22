package com.em.validation.client.core.defects;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_045.Defect45_Example;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;


public class Defect_049 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_49() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		// Defect45_Example can also be used for this test.
		Defect45_Example ex = new Defect45_Example();

		Assert.assertEquals(0, validator.validate(ex).size());
		Assert.assertEquals(1, validator.validate(ex, Default.class, Defect45_Example.Defect45_Group.class).size());

	}
}
