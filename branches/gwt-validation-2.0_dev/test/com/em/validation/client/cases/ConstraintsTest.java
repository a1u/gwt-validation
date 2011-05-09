package com.em.validation.client.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreConstraintsTest;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class ConstraintsTest extends GwtValidationBaseTestCase {
	
	@Test
	public void testConstraintGeneration() {
		CoreConstraintsTest.testConstraintGeneration(this.getReflectorFactory());
	}
	
	@Test
	public void testComposedConstraints() {
		CoreConstraintsTest.testComposedConstraints(this.getReflectorFactory());
	}

}
