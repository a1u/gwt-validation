package com.em.validation.suite.runtime;

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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.reflective.cases.defects.Defect_005;
import com.em.validation.reflective.cases.defects.Defect_024;
import com.em.validation.reflective.cases.defects.Defect_037;
import com.em.validation.reflective.cases.defects.Defect_040;
import com.em.validation.reflective.cases.defects.Defect_041;
import com.em.validation.reflective.cases.defects.Defect_042;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Defect_005.class,
	Defect_024.class,
	Defect_037.class,
	Defect_040.class,
	Defect_041.class,
	Defect_042.class
})
public class DefectTestSuite {

}
