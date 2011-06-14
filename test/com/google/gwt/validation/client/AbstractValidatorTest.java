package com.google.gwt.validation.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class AbstractValidatorTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testIntersectionTrue() {
		
		//list a
		ArrayList<String> listA = new ArrayList<String>();
		listA.add("one");
		listA.add("two");
		listA.add("three");
		
		//list b
		ArrayList<String> listB = new ArrayList<String>();
		listB.add("three");
		listB.add("four");
		listB.add("five");
		
		//create AbstractValidator instance
		AbstractValidator<IValidatable> abs = new AbstractValidator<IValidatable>() {

			@Override
			public Set<InvalidConstraint<IValidatable>> validate(IValidatable object, String... groups) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> validateProperty(IValidatable object, String propertyName, String... groups) {
				return null;
			}

			@Override
			protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(
					Class<?> inputClass) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> performValidation(
					IValidatable object, String propertyName, 
					ArrayList<String> groups, HashSet<String> processedGroups,
					HashSet<String> processedObjects) {
				return null;
			}
			
		};
		
		//assertions		
		assertTrue("List A intersects List B", abs.intersects(listA, listB));
	}
	
	@Test
	public void testIntersectionFalse() {
		
		//list a
		ArrayList<String> listA = new ArrayList<String>();
		listA.add("one");
		listA.add("two");
		listA.add("three");
		
		//list b
		ArrayList<String> listB = new ArrayList<String>();
		listB.add("four");
		listB.add("five");
		listB.add("six");
		
		//create AbstractValidator instance
		AbstractValidator<IValidatable> abs = new AbstractValidator<IValidatable>() {

			@Override
			public Set<InvalidConstraint<IValidatable>> validate(IValidatable object, String... groups) {
				return null;
			}
			
			@Override
			public Set<InvalidConstraint<IValidatable>> validateProperty(IValidatable object, String propertyName, String... groups) {
				return null;
			}

			@Override
			protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(
					Class<?> inputClass) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> performValidation(
					IValidatable object, String propertyName, 
					ArrayList<String> groups, HashSet<String> processedGroups,
					HashSet<String> processedObjects) {
				return null;
			}			
			
		};
		
		//assertions		
		assertFalse("List A intersects List B", abs.intersects(listA, listB));
	}
	
}
