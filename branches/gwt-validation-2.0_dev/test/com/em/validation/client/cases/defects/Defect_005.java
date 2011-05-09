package com.em.validation.client.cases.defects;

import org.junit.Test;

import com.em.validation.client.core.defects.CoreDefect_005;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class Defect_005 extends GwtValidationBaseTestCase {
	
	@Test
	public void testStrangeCapitalization() {
		CoreDefect_005.testStrangeCapitalization(this.getReflectorFactory());
	}
	
}
