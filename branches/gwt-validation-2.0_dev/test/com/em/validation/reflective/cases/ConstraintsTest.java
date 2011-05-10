package com.em.validation.reflective.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreConstraintsTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class ConstraintsTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		CoreConstraintsTest.testConstraintGeneration(this.getReflectorFactory());
	}
	
	@Test
	public void testComposedConstraints() throws InstantiationException, IllegalAccessException {
		CoreConstraintsTest.testComposedConstraints(this.getReflectorFactory());
	}

}
