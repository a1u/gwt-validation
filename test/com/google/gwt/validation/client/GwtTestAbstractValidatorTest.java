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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class GwtTestAbstractValidatorTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

	@Test
	public void testIntersectionTrue() {
		
		//list a
		final ArrayList<String> listA = new ArrayList<String>();
		listA.add("one");
		listA.add("two");
		listA.add("three");
		
		//list b
		final ArrayList<String> listB = new ArrayList<String>();
		listB.add("three");
		listB.add("four");
		listB.add("five");
		
		//create AbstractValidator instance
		final AbstractValidator<IValidatable> abs = new AbstractValidator<IValidatable>() {

			@Override
			public Set<InvalidConstraint<IValidatable>> validate(final IValidatable object, final String... groups) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> validateProperty(final IValidatable object, final String propertyName, final String... groups) {
				return null;
			}

			@Override
			protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(
					final Class<?> inputClass) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> performValidation(
					final IValidatable object, final String propertyName, 
					final ArrayList<String> groups, final HashSet<String> processedGroups,
					final HashSet<String> processedObjects) {
				return null;
			}
			
		};
		
		//assertions		
		assertTrue("List A intersects List B", abs.intersects(listA, listB));
	}
	
	@Test
	public void testIntersectionFalse() {
		
		//list a
		final ArrayList<String> listA = new ArrayList<String>();
		listA.add("one");
		listA.add("two");
		listA.add("three");
		
		//list b
		final ArrayList<String> listB = new ArrayList<String>();
		listB.add("four");
		listB.add("five");
		listB.add("six");
		
		//create AbstractValidator instance
		final AbstractValidator<IValidatable> abs = new AbstractValidator<IValidatable>() {

			@Override
			public Set<InvalidConstraint<IValidatable>> validate(final IValidatable object, final String... groups) {
				return null;
			}
			
			@Override
			public Set<InvalidConstraint<IValidatable>> validateProperty(final IValidatable object, final String propertyName, final String... groups) {
				return null;
			}

			@Override
			protected HashMap<String, ArrayList<String>> getGroupSequenceMapping(
					final Class<?> inputClass) {
				return null;
			}

			@Override
			public Set<InvalidConstraint<IValidatable>> performValidation(
					final IValidatable object, final String propertyName, 
					final ArrayList<String> groups, final HashSet<String> processedGroups,
					final HashSet<String> processedObjects) {
				return null;
			}			
			
		};
		
		//assertions		
		assertFalse("List A intersects List B", abs.intersects(listA, listB));
	}
	
}
