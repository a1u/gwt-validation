package com.em.validation.reflective.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreMetadataTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class MetadataTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testGroupFinderOnClass() {
		CoreMetadataTest.testGroupFinderOnClass(this.getReflectorFactory());
	}
	
	@Test
	public void testGroupFinderOnProperty() {
		CoreMetadataTest.testGroupFinderOnProperty(this.getReflectorFactory());
	}
}
