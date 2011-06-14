package com.em.validation.reflective.cases;

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

import org.junit.Test;

import com.em.validation.client.core.CoreMetadataTest;
import com.em.validation.reflective.model.test.ReflectiveValidationBaseTestClass;

public class MetadataTest extends ReflectiveValidationBaseTestClass {
	
	@Test
	public void testGroupFinderOnClass() {
		CoreMetadataTest.testGroupFinderOnClass(this.getReflectorFactory());
	}
	
	@Test
	public void testGroupFinderOnProperty() {
		CoreMetadataTest.testGroupFinderOnProperty(this.getReflectorFactory());
	}
	
	@Test
	public void testDeclaredOnFinder() {
		CoreMetadataTest.testDeclaredOnFinder(this.getReflectorFactory());
	}
}