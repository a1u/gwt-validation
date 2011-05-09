package com.em.validation.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.em.validation.client.cases.ConstraintsTest;
import com.em.validation.client.cases.MetadataTest;
import com.em.validation.client.cases.defects.DefectTestSuite;
import com.google.gwt.junit.tools.GWTTestSuite;

public class ClientTestSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("GWT Defect Test Suite");
		
		suite.addTest(DefectTestSuite.suite());
		suite.addTestSuite(ConstraintsTest.class);
		suite.addTestSuite(MetadataTest.class);
		
		return suite;
	}
	
}
