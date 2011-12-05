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

import com.em.validation.client.core.ConstraintValidatorFactoryTest;
import com.em.validation.client.core.ConstraintsTest;
import com.em.validation.client.core.MetadataTest;
import com.em.validation.client.core.ValidatorTest;
import com.em.validation.client.core.messages.MessageTest;
import com.em.validation.client.core.reflector.ReflectorTest;
import com.em.validation.reflective.cases.messages.RebindMessageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DefectTestSuite.class,
	ConstraintsTest.class,
	MetadataTest.class,
	ExampleTestSuite.class,
	ValidatorImplementationTestSuite.class,
	ConstraintValidatorFactoryTest.class,
	ValidatorTest.class,
	MessageTest.class,
	RebindMessageTest.class,
	ReflectorTest.class
})
public class ReflectiveTestSuite {

}
