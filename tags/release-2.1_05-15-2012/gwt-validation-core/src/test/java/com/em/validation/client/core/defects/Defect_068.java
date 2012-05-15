package com.em.validation.client.core.defects;

import java.lang.annotation.Annotation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.defects.defect_068.AllowedValues;
import com.em.validation.client.model.defects.defect_068.Defect68_TestData;
import com.em.validation.client.model.defects.defect_068.Values;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_068 extends GwtValidationBaseTestCase {

	@Test
	public void testBehaviorOfIssue_68() {

		//get the validator factory
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		//get the descriptor for the class under test (Defect68_TestData) and then
		BeanDescriptor descriptor = validator.getConstraintsForClass(Defect68_TestData.class);
		//get the constraints for that property (of which there should be one)
		PropertyDescriptor propertyDescriptor = descriptor.getConstraintsForProperty("value");
		
		//expecting only one constraint descriptor
		Assert.assertEquals("Unexpected amount of constraint descriptors.", 1, propertyDescriptor.getConstraintDescriptors().size());
		
		//now to get that descriptor
		ConstraintDescriptor<?> constraint = propertyDescriptor.getConstraintDescriptors().iterator().next();
		
		//now to inspect it
		Annotation constraintAnnotation = constraint.getAnnotation();
		if(constraintAnnotation instanceof AllowedValues) {
			AllowedValues aValuesAnnotation = (AllowedValues)constraintAnnotation;
			Values[] values = aValuesAnnotation.value();
			//assert that both foo and bar are in the value set
			Assert.assertTrue("The value FOO should be in the value list.", Values.FOO.equals(values[0]) || Values.FOO.equals(values[1]));
			Assert.assertTrue("The value BAR should be in the value list.", Values.BAR.equals(values[0]) || Values.BAR.equals(values[1]));
		} else {
			Assert.fail("The found annotation must be an instance of @AllowedValues");
		}
		
		Defect68_TestData passes = new Defect68_TestData();
		passes.setValue(Values.FOO);
		
		Assert.assertEquals("There should be no violations.", 0, validator.validateProperty(passes, "value").size());
		
		passes.setValue(Values.BAR);
		
		Assert.assertEquals("There should be no violations.", 0, validator.validateProperty(passes, "value").size());
		
		Defect68_TestData fails = new Defect68_TestData();
		fails.setValue(Values.BAZ);
		
		Assert.assertEquals("There should be one violation.", 1, validator.validateProperty(fails, "value").size());
	}

}