package com.em.validation.rebind;

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

import java.util.Set;

import org.junit.Test;

import com.em.validation.client.model.generic.ExtendedInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.rebind.scan.ConstrainedClassScanner;

import static org.junit.Assert.*;

public class ConstrainedClassScannerTest {

	@Test
	public void testConstrainedClassScanner() {
		//get the set of constrained classes on the classpath
		Set<Class<?>> classes = ConstrainedClassScanner.INSTANCE.getConstrainedClasses();
		
		//verify
		assertTrue(classes.contains(ExtendedInterface.class));
		assertTrue(classes.contains(TestClass.class));
	}
	
	public void testPatternMatchingConsrainedClassScanner() {
		//get the constrained classes not matching the pattern "blah"
		Set<Class<?>> classes = ConstrainedClassScanner.INSTANCE.getConstrainedClasses(".*\\.groups\\..*");
	
		//verify
		assertFalse(classes.contains(ExtendedInterface.class));
		assertFalse(classes.contains(TestClass.class));
		assertTrue(classes.contains(GroupTestClass.class));
	}
	
}
