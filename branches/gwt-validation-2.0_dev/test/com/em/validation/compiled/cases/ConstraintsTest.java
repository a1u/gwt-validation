package com.em.validation.compiled.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreConstraintsTest;
import com.em.validation.compiled.model.test.CompiledValidationBaseTestClass;

public class ConstraintsTest extends CompiledValidationBaseTestClass {
	
	@Test
	public void testConstraintGeneration() throws InstantiationException, IllegalAccessException {
		CoreConstraintsTest.testConstraintGeneration(this.getReflectorFactory());
	}
	
	@Test
	public void testComposedConstraints() throws InstantiationException, IllegalAccessException {
		CoreConstraintsTest.testComposedConstraints(this.getReflectorFactory());
	}

}
