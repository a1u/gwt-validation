package com.em.validation.compiled.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_024;
import com.em.validation.compiled.model.test.CompiledValidationBaseTestClass;

public class Defect_024 extends CompiledValidationBaseTestClass {
	
	@Test
	public void testPatternEscapes() {
		CoreDefect_024.testPatternEscapes(this.getReflectorFactory());
	}
	
}
