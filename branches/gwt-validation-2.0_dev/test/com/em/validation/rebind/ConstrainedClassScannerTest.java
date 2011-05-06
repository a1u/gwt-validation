package com.em.validation.rebind;

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
