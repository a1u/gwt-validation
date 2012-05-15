package com.em.validation.client.core.defects;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import junit.framework.Assert;

import org.junit.Test;

import com.em.validation.client.model.defects.defect_070.PatternTestClass;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_070 extends GwtValidationBaseTestCase {
	
	@Test
	public void testBehaviorOfIssue_70() {

		//get the validator factory and the validator
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
				
		PatternTestClass patternTest = new PatternTestClass();

		BeanDescriptor descriptor = validator.getConstraintsForClass(PatternTestClass.class);
		PropertyDescriptor propertyDescriptor = descriptor.getConstraintsForProperty("testString");
		
		Set<ConstraintDescriptor<?>> constraintDescriptors = propertyDescriptor.getConstraintDescriptors();
		Assert.assertEquals("Should be three constraints.", 4, constraintDescriptors.size());
		
		Set<ConstraintViolation<PatternTestClass>> violations = validator.validate(patternTest);
		Assert.assertEquals("Should be one violation", 1, violations.size());
		
		patternTest.setTestString("");
		
		violations = validator.validate(patternTest);
		//for(ConstraintViolation<PatternTestClass> cv : violations) {
		//	System.out.println(cv);
		//}
		Assert.assertEquals("Should still be one violation", 1, violations.size());
		
		patternTest.setTestString("abc");
		
		violations = validator.validate(patternTest);
		//for(ConstraintViolation<PatternTestClass> cv : violations) {
		//	System.out.println(cv);
		//}
		Assert.assertEquals("Should be two violations (number, short letter)", 2, violations.size());
		
		patternTest.setTestString("ab");
		
		violations = validator.validate(patternTest);
		//for(ConstraintViolation<PatternTestClass> cv : violations) {
		//	System.out.println(cv);
		//}
		Assert.assertEquals("Should be one violation (number)", 1, violations.size());
		
		patternTest.setTestString("123");
		
		violations = validator.validate(patternTest);
		//for(ConstraintViolation<PatternTestClass> cv : violations) {
		//	System.out.println(cv);
		//}
		Assert.assertEquals("Should be one violation (letter, short letter)", 2, violations.size());
	}
}
