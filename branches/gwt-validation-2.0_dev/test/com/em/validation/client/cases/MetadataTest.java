package com.em.validation.client.cases;

import org.junit.Test;

import com.em.validation.client.core.CoreMetadataTest;
import com.em.validation.client.model.tests.GwtValidationBaseTestCase;

public class MetadataTest extends GwtValidationBaseTestCase {
	
	@Test
	public void testGroupFinderOnClass() {
		CoreMetadataTest.testGroupFinderOnClass(this.getReflectorFactory());
	}
	
	@Test
	public void testGroupFinderOnProperty() {
		CoreMetadataTest.testGroupFinderOnProperty(this.getReflectorFactory());
	}
}
