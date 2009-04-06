package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AbstractValidatorTest.class,
	AssertFalseTest.class,
	AssertTrueTest.class,
	ClientValidatorTest.class,
	LengthTest.class,
	MaxTest.class,
	MinTest.class,
	NotEmptyTest.class,
	NotNullTest.class,
	RangeTest.class,
	PatternTest.class,
	PastTest.class,
	FutureTest.class,
	SizeTest.class,
	EmailTest.class,
	CodeMuseumTest.class
})
public class ClientTestSuite {

}
