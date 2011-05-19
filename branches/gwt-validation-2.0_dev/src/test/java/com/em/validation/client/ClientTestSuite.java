package com.em.validation.client;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import junit.framework.Test;
import junit.framework.TestSuite;

import com.em.validation.client.cases.ConstraintsTest;
import com.em.validation.client.cases.MetadataTest;
import com.em.validation.client.cases.defects.DefectTestSuite;
import com.em.validation.client.cases.example.ExampleTestSuite;
import com.google.gwt.junit.tools.GWTTestSuite;

public class ClientTestSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("GWT Client Test Suite");
		
		suite.addTest(DefectTestSuite.suite());
		suite.addTestSuite(ConstraintsTest.class);
		suite.addTestSuite(MetadataTest.class);
		suite.addTest(ExampleTestSuite.suite());
		
		return suite;
	}
	
}
