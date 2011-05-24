package com.em.validation.compiled;

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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.compiled.cases.ConstraintValidatorFactoryTest;
import com.em.validation.compiled.cases.ConstraintsTest;
import com.em.validation.compiled.cases.MetadataTest;
import com.em.validation.compiled.cases.defects.DefectSuite;
import com.em.validation.compiled.cases.example.ExampleTestSuite;
import com.em.validation.compiled.cases.validators.ValidatorTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DefectSuite.class,
	ConstraintsTest.class,
	MetadataTest.class,
	ExampleTestSuite.class,
	ValidatorTestSuite.class,
	ConstraintValidatorFactoryTest.class
})
public class CompiledTestSuite {

}
