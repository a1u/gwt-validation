package com.em.validation.client.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_024;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_024 extends GwtValidationBaseTestCase {
	
	@Test
	public void testPatternEscapes() {
		CoreDefect_024.testPatternEscapes(this.getReflectorFactory());
	}
	
}
