package com.em.validation.compiled.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_005;
import com.em.validation.compiled.model.test.CompiledValidationBaseTestClass;

public class Defect_005 extends CompiledValidationBaseTestClass {
	
	@Test
	public void testStrangeCapitalization() {
		CoreDefect_005.testStrangeCapitalization(this.getReflectorFactory());
	}
	
}
