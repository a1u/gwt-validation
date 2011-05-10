package com.em.validation.reflective.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_005;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class Defect_005 extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testStrangeCapitalization() {
		CoreDefect_005.testStrangeCapitalization(this.getReflectorFactory());
	}
	
}
