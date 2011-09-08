package com.em.validation.suite.client;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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

import com.em.validation.client.cases.example.jsr303.section5_6.Section5_6Test;
import com.google.gwt.junit.tools.GWTTestSuite;

public class ExampleTestSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("GWT JSR-303 Example Test Suite");
		
		suite.addTestSuite(Section5_6Test.class);
		
		return suite;
	}
	
}
