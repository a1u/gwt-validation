package com.em.validation.reflective.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_024;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class Defect_024 extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testPatternEscapes() {
		CoreDefect_024.testPatternEscapes(this.getReflectorFactory());
	}
	
}
