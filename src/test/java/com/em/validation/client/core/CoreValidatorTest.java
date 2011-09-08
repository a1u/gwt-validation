package com.em.validation.client.core;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.client.ValidatorImpl;
import com.em.validation.client.model.cyclic.Cycle;
import com.em.validation.client.model.generic.ParentClass;
import com.em.validation.client.model.generic.ParentInterface;
import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.model.sequence.ClassWithSequence;
import com.em.validation.client.model.tests.ITestCase;
import com.em.validation.client.model.validator.ChildNode;
import com.em.validation.client.model.validator.ClassWithArray;
import com.em.validation.client.model.validator.ClassWithIterable;
import com.em.validation.client.model.validator.ClassWithMap;
import com.em.validation.client.reflector.IReflector;
import com.em.validation.client.validators.size.SizeStringValidator;

public class CoreValidatorTest {

	
	public static void testCyclicValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Cycle a = new Cycle();
		Cycle b = new Cycle();
		
		a.setOther(b);
		b.setOther(a);
		
		//validated cyclic object
		Set<ConstraintViolation<Cycle>> violations = validator.validate(a);
		
		testCase.localAssertEquals(3, violations.size());
	}
	
	public static void testRecurisveValidator(ITestCase testCase) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Cycle recursive = new Cycle();
		recursive.setOther(recursive);
		
		//validated cyclic object
		Set<ConstraintViolation<Cycle>> violations = validator.validate(recursive);
		
		testCase.localAssertEquals(2, violations.size());
	}
	
	public static void testMapValidator(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		ClassWithMap test = new ClassWithMap();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<ClassWithMap>> preViolations = validator.validate(test);
		
		//min size violation
		testCase.localAssertFalse(preViolations.isEmpty());
		
		//set up test map with a few string keys and child nodes (which automatically produce 2 violations each)
		test.getTestMap().put("one", new ChildNode());
		test.getTestMap().put("two", new ChildNode());
		test.getTestMap().put("three", new ChildNode());
		
		//get violations again
		Set<ConstraintViolation<ClassWithMap>> violations = validator.validate(test);
		
		testCase.localAssertEquals(6, violations.size());
		
		//begin looking through violations, ensuring that the key shows up in each violation path
		for(ConstraintViolation<ClassWithMap> violation : violations) {
			String path = violation.getPropertyPath().toString().toLowerCase();
			testCase.localAssertTrue(path.contains("one") || path.contains("two") || path.contains("three"));
		}
	}
	
	public static void testArrayValidator(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		ClassWithArray test = new ClassWithArray();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<ClassWithArray>> preViolations = validator.validate(test);
		
		//min size violation
		testCase.localAssertNotNull(preViolations);
		testCase.localAssertFalse("violations should not be empty", preViolations.isEmpty());
		
		//set up test map with a few string keys and child nodes (which automatically produce 2 violations each)
		ChildNode[] array = new ChildNode[]{
			new ChildNode(),
			new ChildNode(),
			new ChildNode()
		};
		test.setTestArray(array);
		
		//get violations again
		Set<ConstraintViolation<ClassWithArray>> violations = validator.validate(test);
		
		testCase.localAssertEquals(6, violations.size());
		
		//begin looking through violations, ensuring that the key shows up in each violation path
		for(ConstraintViolation<ClassWithArray> violation : violations) {
			String path = violation.getPropertyPath().toString().toLowerCase();
			testCase.localAssertTrue(path.contains("[0]") || path.contains("[1]") || path.contains("[2]"));
		}
	}
	
	public static void testIterableValidator(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		ClassWithIterable test = new ClassWithIterable();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<ClassWithIterable>> preViolations = validator.validate(test);
		
		//min size violation
		testCase.localAssertNotNull(preViolations);
		testCase.localAssertFalse("violations should not be empty", preViolations.isEmpty());
		
		//set up test map with a few string keys and child nodes (which automatically produce 2 violations each)
		List<ChildNode> nodeList = new ArrayList<ChildNode>();
		nodeList.add(new ChildNode());
		nodeList.add(new ChildNode());
		nodeList.add(new ChildNode());
		test.setTestIterable(nodeList);
		
		//get violations again
		Set<ConstraintViolation<ClassWithIterable>> violations = validator.validate(test);
		
		testCase.localAssertEquals(6, violations.size());
		
		//begin looking through violations, ensuring that the key shows up in each violation path
		for(ConstraintViolation<ClassWithIterable> violation : violations) {
			String path = violation.getPropertyPath().toString().toLowerCase();
			testCase.localAssertTrue(path.contains("[0]") || path.contains("[1]") || path.contains("[2]"));
		}
	}
	
	/**
	 * This is a variation on testCyclicValidator that tests each one as a property and then as a value validation
	 * 
	 * @param testCase
	 */
	public static void testPropertyValidationCyclic(ITestCase testCase) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Cycle a = new Cycle();
		Cycle b = new Cycle();
		
		a.setOther(b);
		b.setOther(a);
		
		//validated cyclic object
		Set<ConstraintViolation<Cycle>> violations1 = validator.validateProperty(a,"other");
		Set<ConstraintViolation<Cycle>> violations2 = validator.validateProperty(b,"other");
		Set<ConstraintViolation<Cycle>> violations3 = validator.validateValue(Cycle.class,"other",b);
		
		//not that this is LESS violations [than testCyclicValidator] because we're directly testing a property, not testing the entire object!
		testCase.localAssertEquals(2, violations1.size());
		testCase.localAssertEquals(2, violations2.size());
		testCase.localAssertEquals(2, violations3.size());
	}
	
	public static void testUnwrap(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		Validator second = validator.unwrap(ValidatorImpl.class);
		
		testCase.localAssertEquals(validator.getClass(), second.getClass());
		
		try {
			validator.unwrap(Validation.class);
			testCase.localAssertTrue("did not throw an exception (unexpected)", false);
		} catch (ValidationException ex) {
			testCase.localAssertTrue("threw an exception", true);
		}
	}
	
	public static void testGroupSequenceValidation(ITestCase testCase) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		Validator validator = factory.getValidator();
		
		ClassWithSequence cws = new ClassWithSequence();
		
		Set<ConstraintViolation<ClassWithSequence>> violations = validator.validate(cws);
		
		testCase.localAssertEquals(0, violations.size());
		
		//set all properties to break
		cws.setNotEmpty("");
		cws.setShouldBeTrue(false);
		cws.setShouldBeFalse(true);
		
		violations = validator.validate(cws);
		testCase.localAssertEquals(1, violations.size());
		
		cws.setNotEmpty("notempty");
		
		violations = validator.validate(cws);
		testCase.localAssertEquals(1, violations.size());
		
		cws.setShouldBeTrue(true);
		
		violations = validator.validate(cws);
		testCase.localAssertEquals(1, violations.size());		
	}
	
	public static void testImplicitGroupValidation(ITestCase testCase) {
		//get reflectors
		IReflector<ParentClass> parentClassReflector = testCase.getReflectorFactory().getReflector(ParentClass.class);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		//check descriptors first
		BeanDescriptor parentClassDescriptor = validator.getConstraintsForClass(ParentClass.class);
		BeanDescriptor parentInterfaceDescriptor = validator.getConstraintsForClass(ParentInterface.class);
		
		for(PropertyDescriptor pDesc : parentClassDescriptor.getConstrainedProperties()) {
			String name = pDesc.getPropertyName();
			testCase.localAssertTrue("Property descriptor for " + name, "parentInt".equals(name) || "publicParentString".equals(name) || "parentAbstractString".equals(name) || "parentInterfaceInt".equals(name));
		}
		
		for(PropertyDescriptor pDesc : parentInterfaceDescriptor.getConstrainedProperties()) {
			String name = pDesc.getPropertyName();
			testCase.localAssertTrue("Property descriptor for " + name, "parentInterfaceInt".equals(name));
		}
		
		//create class
		TestClass testClass = new TestClass();
		
		//set breaking values
		testClass.setParentInt(-22);
		testClass.publicParentString = "do";
		
		//check reflection at the parent class level
		testCase.localAssertEquals("do", parentClassReflector.getValue("publicParentString", testClass));
		PropertyDescriptor pPSDesc = parentClassDescriptor.getConstraintsForProperty("publicParentString");
		
		//test constraint descriptors to ensure that the right ones are being found
		testCase.localAssertEquals(2,pPSDesc.getConstraintDescriptors().size());
		for(ConstraintDescriptor<?> cDesc : pPSDesc.getConstraintDescriptors()) {
			testCase.localAssertTrue(Size.class.equals(cDesc.getAnnotation().annotationType()) || NotNull.class.equals(cDesc.getAnnotation().annotationType()));
			if(Size.class.equals(cDesc.getAnnotation().annotationType())) {
				testCase.localAssertEquals("The value \"4\" is set on the constraint", 4,cDesc.getAttributes().get("min"));
				testCase.localAssertFalse("Must contain at least one validator", cDesc.getConstraintValidatorClasses().isEmpty());
				testCase.localAssertTrue("The validator list must contain SizeStringValidator", cDesc.getConstraintValidatorClasses().contains(SizeStringValidator.class));
			}
		}
		
		//validate class
		Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass, ParentClass.class, ParentInterface.class);
		
		//should be 3! parentInterfaceInt, parentIn, and publicParentString
		testCase.localAssertEquals("Expecting violations for both ParentClass and ParentInterface", 3, violations.size());
		
		for(ConstraintViolation<TestClass> violation : violations) {
			String path = violation.getPropertyPath().toString();
			testCase.localAssertTrue("Property path is: " + path, "parentInt".equals(path) || "publicParentString".equals(path) || "parentInterfaceInt".equals(path));
		}
		
		//re-validate without ParentClass
		violations = validator.validate(testClass, ParentInterface.class);
		
		testCase.localAssertEquals("Expecting only one violation directly on the parent interface class (implicit group)", 1, violations.size());
	}
}


