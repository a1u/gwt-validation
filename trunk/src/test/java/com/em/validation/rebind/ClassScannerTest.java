package com.em.validation.rebind;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintValidator;

import org.junit.Test;

import com.em.validation.client.model.generic.ExtendedInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.groups.GroupTestClass;
import com.em.validation.rebind.scan.ClassScanner;

public class ClassScannerTest {

	@Test
	public void testConstrainedClassScanner() {
		//get the set of constrained classes on the classpath
		Set<Class<?>> classes = ClassScanner.INSTANCE.getConstrainedClasses();
		
		//verify
		assertTrue(classes.contains(ExtendedInterface.class));
		assertTrue(classes.contains(TestClass.class));
		assertTrue(classes.contains(GroupTestClass.class));
	}
	
	@Test
	public void testPatternMatchingConsrainedClassScanner() {
		//get the constrained classes not matching the given pattern
		Set<Class<?>> classes = ClassScanner.INSTANCE.getConstrainedClasses(".*groups.*");
	
		//verify
		assertTrue(classes.contains(ExtendedInterface.class));
		assertTrue(classes.contains(TestClass.class));
		assertFalse(classes.contains(GroupTestClass.class));
	}
	
	@Test
	public void testPatternMatchingValidatorClassScanner() {
		//get the constrained classes not matching the given pattern
		Set<Class<? extends ConstraintValidator<?, ?>>> classes = ClassScanner.INSTANCE.getConstraintValidatorClasses(".*");
	
		//verify
		assertEquals(0, classes.size());
	}
	
}
