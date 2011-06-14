package com.google.gwt.validation.rebind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.google.gwt.validation.client.AssertFalse;
import com.google.gwt.validation.client.AssertTrue;
import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.Patterns;
import com.google.gwt.validation.client.Range;
import com.google.gwt.validation.client.test.AnnotatedClass;
import com.google.gwt.validation.client.test.AnnotatedSuperClass;
import com.google.gwt.validation.client.test.GroupSequenceClassTest;
import com.google.gwt.validation.client.test.ObjectGraphTest;

public class ValidationMetadataFactoryTest {

	@Test
	public void testPatterns() {
		
		@SuppressWarnings("unused")
		class TestPatternsClass {
			
			@Patterns({
				@Pattern(pattern=".", message="pattern 1"),
				@Pattern(pattern=".", message="pattern 2"),
				@Pattern(pattern=".", message="pattern 3"),
				@Pattern(pattern=".", message="pattern 4")
			})			
			private String patternTestCase;

			public void setPatternTestCase(String patternTestCase) {
				this.patternTestCase = patternTestCase;
			}

			public String getPatternTestCase() {
				return patternTestCase;
			}
			
		}
		
		//get packages
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(TestPatternsClass.class);
		
		//assertions
		assertEquals("Should have 4 pattern validation packages", 4, vplist.size());
		
	}
	
	@Test
	public void testPattern() {
	
		@SuppressWarnings("unused")
		class TestPatternClass {
			
			@Pattern
			private String patternTestCase;

			public void setPatternTestCase(String patternTestCase) {
				this.patternTestCase = patternTestCase;
			}

			public String getPatternTestCase() {
				return patternTestCase;
			}
			
		}
		
		//get packages
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(TestPatternClass.class);
		
		//assertions
		assertEquals("Should have 1 pattern validation packages", 1, vplist.size());		
	}
	
	@Test
	public void testMultipleAssertionsOnSingleField() {
		
		@SuppressWarnings("unused")
		class TestMAClass {
			
			@Patterns({
				@Pattern,
				@Pattern,
				@Pattern
			})
			@NotNull
			@NotEmpty
			@Length
			//the following would fail, but added anyway
			@AssertTrue
			@AssertFalse
			@Range
			@Min
			@Max
			//11 validators total for one field			
			private String patternTestCase;

			public void setPatternTestCase(String patternTestCase) {
				this.patternTestCase = patternTestCase;
			}

			public String getPatternTestCase() {
				return patternTestCase;
			}
		}
		
		//get packages
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(TestMAClass.class);
		
		//assertions
		assertEquals("Should have 11 validation packages", 11, vplist.size());
		
	}
	
	@Test
	public void testAssertionsAcrossAll() {
		
		@SuppressWarnings("unused")
		class TesterClass {

			@NotNull
			@NotEmpty
			@Patterns({
				@Pattern,
				@Pattern
			})
			//4
			private String testString;
			
			@AssertTrue
			@AssertFalse
			//2
			private boolean testBoolean;

			@AssertTrue
			//1
			public boolean testBooleanMethod() {
				return true;
			}
			
			@Length
			@NotEmpty
			//2
			public String testStringMethod() {
				return "";
			}
			
			@AssertFalse
			//should not count
			private boolean testExcludedMethod() {
				return false;
			}
			
			@NotNull
			//should not count
			private void testExcludedPrivateVoid() {
				
			}
			
			@NotNull
			//should not count
			public void testExcludedPublicVoid() {
				
			}

			public void setTestString(String testString) {
				this.testString = testString;
			}

			public String getTestString() {
				return testString;
			}

			public void setTestBoolean(boolean testBoolean) {
				this.testBoolean = testBoolean;
			}

			public boolean isTestBoolean() {
				return testBoolean;
			}
			
			//should create 9 validation packages
		}
		
	
		//get packages
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(TesterClass.class);
		
		//assertions
		assertEquals("Should have 9 validation packages", 9, vplist.size());
	}
	
	/**
	 * Private interface for testing inheritence (1)
	 * 
	 * @author chris
	 *
	 */
	private interface SuperInterface1 {
	
		@NotEmpty
		public String interfaceStringTest1();
		
	}
	
	/**
	 * Private interface for testing inheritence (2)
	 *  
	 * @author chris
	 *
	 */
	private interface SuperInterface2 extends SuperInterface1 {
		
		@NotNull
		public String interfaceStringTest2();
		
	}
	
	private interface MiddleInterface1 {
		
		@NotEmpty
		public String interfaceTest4();
		
	}
	
	/**
	 * Private interface for testing inheritence (3)
	 * 
	 * @author chris
	 *
	 */
	private interface SubInterface1 {
		
		@Length(minimum=0, maximum=27)
		public String interfaceStringTest3();
	}
	
	
	@Test 
	public void testInheritence() {
		
		
		@SuppressWarnings("unused")
		class SuperClass implements SuperInterface2{
			
			@NotEmpty
			protected String innerString;

			public void setInnerString(String innerString) {
				this.innerString = innerString;
			}

			public String getInnerString() {
				return innerString;
			}

			public String interfaceStringTest2() {
				return null;
			}

			public String interfaceStringTest1() {
				return null;
			}			
		}		
		
		@SuppressWarnings("unused")
		class MiddleClass extends SuperClass implements MiddleInterface1 {
			
			public String middleStringTest() {
				return null;
			}

			public String interfaceTest4() {
				return null;
			}			
			
		}
		
		@SuppressWarnings("unused")
		class SubClass extends MiddleClass implements SubInterface1 {
			
			@NotNull
			@NotEmpty
			private String outerString;

			public void setOuterString(String outerString) {
				this.outerString = outerString;
			}

			public String getOuterString() {
				return outerString;
			}

			@Length(minimum=2,maximum=5)
			public String interfaceStringTest3() {
				return null;
			}
			
			@NotNull
			public String middleStringTest() {
				return "";
			}
			
		}
		
		//get packages
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(SubClass.class);

		//should be 10
		assertEquals("Should have 9 validation packages [4 from SubClass, 1 from SuperClass, 1 from SubInterface1, 1 from MiddleInterface1, 1 from SuperInterface1, 1 from SuperInterface2].", 9, vplist.size());
	}
	
	@Test
	public void testClassLevel() {
		//get packages for class
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(AnnotatedSuperClass.class);
	
		//assert that it does NOT show up at the package level
		assertEquals("Field/Method level should be 0.", 0, vplist.size());
		
		//get class level packages
		vplist = ValidationMetadataFactory.getClassLevelValidatorsForClass(AnnotatedSuperClass.class);
		
		//assert that it shows up at the class level
		assertEquals("Class level should be 1", 1, vplist.size());
	}
	
	@Test
	public void testClassLevelWithInheritence() {
		
		//get packages for class
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(AnnotatedClass.class);
	
		//assert that it does NOT show up at the package level
		assertEquals("Field/Method level should be 0.", 0, vplist.size());
		
		//get class level packages
		vplist = ValidationMetadataFactory.getClassLevelValidatorsForClass(AnnotatedClass.class);
		
		//assert that it shows up at the class level
		assertEquals("Class level should be 4", 4, vplist.size());
		
	}
	
	@Test
	public void testBasicObjectGraphValidation() {
		
		//get packages for method/fields
		ArrayList<ValidationPackage> vplist = ValidationMetadataFactory.getValidatorsForClass(ObjectGraphTest.class);
		
		//assert two because of two not nulls
		assertEquals("There should be two field/method level annotations.", 2, vplist.size());
		
		//get packages for object graph (further validation)
		vplist = ValidationMetadataFactory.getValidAnnotedPackages(ObjectGraphTest.class);

		//assert six because of two @valid
		assertEquals("There should be 6 @Valid annotations.", 6, vplist.size());
	}
	
	@Test
	public void testGroupSequenceMetadata() {
		
		//get group sequence list for class
		HashMap<String, ArrayList<String>> orderTable = ValidationMetadataFactory.getGroupSequenceMap(GroupSequenceClassTest.class);
	
		//assertions
		assertTrue("Contains group 'default'.",orderTable.containsKey("default"));
		assertTrue("Contains group 'one'.",orderTable.containsKey("one"));
		assertTrue("Contains group 'two'.",orderTable.containsKey("two"));
		assertTrue("Contains group 'three'.",orderTable.containsKey("three"));
		
		//get lists
		ArrayList<String> one = orderTable.get("one");
		ArrayList<String> two = orderTable.get("two");
		ArrayList<String> three = orderTable.get("three");
		ArrayList<String> defaultList = orderTable.get("default");
		
		//assert not null
		assertNotNull("Group sequence one is not null.", one);
		assertNotNull("Group sequence two is not null.", two);
		assertNotNull("Group sequence three is not null.", three);
		
		//check elements
		assertEquals("Group one contains 'one.first' at index 0.", "one.first", one.get(0));
		assertEquals("Group one contains 'one.second' at index 1.", "one.second", one.get(1));
		
		assertEquals("Group two contains 'two.first' at index 0.", "two.first", two.get(0));
		assertEquals("Group two contains 'two.second' at index 1.", "two.second", two.get(1));

		assertEquals("Group three contains 'three.first' at index 0.", "three.first", three.get(0));
		assertEquals("Group three contains 'three.second' at index 1.", "three.second", three.get(1));
		
		assertEquals("Group default contains 'one.first' at index 0.", "one.first", defaultList.get(0));
		assertEquals("Group default contains 'two.first' at index 1.", "two.first", defaultList.get(1));
		assertEquals("Group default contains 'three.first' at index 2.", "three.first", defaultList.get(2));

		
	}
}
