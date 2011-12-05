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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.client.gwt.defects.GwtDefect_005;
import com.em.validation.client.gwt.defects.GwtDefect_024;
import com.em.validation.client.gwt.defects.GwtDefect_037;
import com.em.validation.client.gwt.defects.GwtDefect_040;
import com.em.validation.client.gwt.defects.GwtDefect_041;
import com.em.validation.client.gwt.defects.GwtDefect_042;
import com.em.validation.client.gwt.defects.GwtDefect_043;
import com.em.validation.client.gwt.defects.GwtDefect_045;
import com.em.validation.client.gwt.defects.GwtDefect_049;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GwtDefect_005.class,
	GwtDefect_024.class,
	GwtDefect_037.class,
	GwtDefect_040.class,
	GwtDefect_041.class,
	GwtDefect_042.class,
	GwtDefect_043.class,
	GwtDefect_045.class,
	GwtDefect_049.class
})
public class GwtDefectTestSuite {

}
