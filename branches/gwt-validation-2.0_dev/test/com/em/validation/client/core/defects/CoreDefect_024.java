package com.em.validation.client.core.defects;

import org.junit.Test;

import com.em.validation.client.model.defects.defect_024.TestPattern;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.IReflectorFactory;

public class CoreDefect_024 extends GwtValidationBaseTestCase {

	@Test
	public static void testPatternEscapes(IReflectorFactory factory) {
		
		//get pattern reflector
		IReflector<TestPattern> testPatternReflector = factory.getReflector(TestPattern.class);
		
		//check out pattern 
		assertNotNull(testPatternReflector);
		
	}

}
