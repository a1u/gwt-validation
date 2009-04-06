package com.google.gwt.validation.server;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.validation.client.Size;
import com.google.gwt.validation.client.SizeValidator;

public class SizeServerTest {

	private Size s = new Size() {

		public String[] groups() {
			return null;
		}

		public int maximum() {
			return 3;
		}

		public String message() {
			return null;
		}

		public int minimum() {
			return 2;
		}

		public Class<? extends Annotation> annotationType() {
			return null;
		}
		
	};
	
	private SizeValidator sv = new SizeValidator();
	
	@Before
	public void setupConstraint() {
		this.sv.initialize(this.s);
	}
	
	public void gwtSetUp() {
		this.setupConstraint();
	}
	
	@Test
	public void testArrayInSize() {
	
		//create array
		int[] intArray = new int[]{1,2};
		
		//assert
		assertTrue("The array of 2 is within the minimum and maximum.", this.sv.isValid(intArray));
	}
	
	@Test
	public void testArrayOverSize() {

		//create array
		int[] intArray = new int[]{1,2,3,4,5,6};
		
		//assert
		assertFalse("The array of 6 is over the maximum.", this.sv.isValid(intArray));
	}
	
	@Test
	public void testArrayUnderSize() {

		//create array
		String[] stringArray = new String[]{"one"};
				
		//assert
		assertFalse("The array of 1 is under the minimum.", this.sv.isValid(stringArray));
	}
	
	@Test
	public void testCollectionInSize() {
		
		//build collection
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("one");
		collection.add("two");
		collection.add("three");
		
		//assert
		assertTrue("The collection of 3 is within the minimum and maximum.", this.sv.isValid(collection));
	}
	
	@Test
	public void testCollectionOverSize() {
		
		//build collection
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("one");
		collection.add("two");
		collection.add("three");
		collection.add("four");
		collection.add("five");
		
		//assert
		assertFalse("The array of 5 is over the maximum.", this.sv.isValid(collection));
	}
	
	@Test
	public void testCollectionUnderSize() {

		//build collection
		ArrayList<String> collection = new ArrayList<String>();
		collection.add("one");
		
		//assert
		assertFalse("The array of 1 is under the minimum.", this.sv.isValid(collection));
	}
	
	@Test
	public void testMapInSize() {
		
		//build map
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("one", "one-dash");
		map.put("two", "two-dash");
		
		//assert
		assertTrue("The map of 2 is within the minimum and maximum.", this.sv.isValid(map));
	}
	
	@Test
	public void testMapOverSize() {
		
		//build map
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("one", "one-dash");
		map.put("two", "two-dash");
		map.put("three", "three-dash");
		map.put("four", "four-dash");

		//assert
		assertFalse("The map of 4 is over the maximum.", this.sv.isValid(map));
	}
	
	@Test
	public void testMapUnderSize() {
		
		//build map
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("one", "one-dash");
		
		//assert
		assertFalse("The map of 1 is under the minimum.", this.sv.isValid(map));
	}
	
}
