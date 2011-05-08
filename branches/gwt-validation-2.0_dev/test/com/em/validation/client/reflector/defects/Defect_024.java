package com.em.validation.client.reflector.defects;

import org.junit.Test;

import com.em.validation.client.GwtValidationBaseTestCase;
import com.em.validation.client.model.defects.defect_024.TestPattern;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.reflector.ReflectorFactory;

public class Defect_024 extends GwtValidationBaseTestCase {

	@Test
	public void testPatternEscapes() {
		
		//get pattern reflector
		IReflector<TestPattern> testPatternReflector = ReflectorFactory.INSTANCE.getReflector(TestPattern.class);
		
		//check out pattern 
		assertNotNull(testPatternReflector);
		
	}

}
