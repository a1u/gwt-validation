package com.em.validation.compiled.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreMetadataTest;
import com.em.validation.compiled.model.test.CompiledValidationBaseTestClass;

public class MetadataTest extends CompiledValidationBaseTestClass {
	
	@Test
	public void testGroupFinderOnClass() {
		CoreMetadataTest.testGroupFinderOnClass(this.getReflectorFactory());
	}
	
	@Test
	public void testGroupFinderOnProperty() {
		CoreMetadataTest.testGroupFinderOnProperty(this.getReflectorFactory());
	}
}
