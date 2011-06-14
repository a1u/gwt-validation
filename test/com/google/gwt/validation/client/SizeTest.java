package com.google.gwt.validation.client;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.Size;
import com.google.gwt.validation.client.SizeValidator;

public class SizeTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}

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
