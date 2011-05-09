package com.em.validation.client.cases.defects;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

public class DefectTestSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("GWT Defect Test Suite");
		
		suite.addTestSuite(Defect_005.class);
		suite.addTestSuite(Defect_024.class);
		
		return suite;
	}
	
}
