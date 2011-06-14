package com.google.gwt.validation.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IValidator;
import com.google.gwt.validation.client.test.AnnotatedClass;
import com.google.gwt.validation.client.test.AnnotatedSuperClass;
import com.google.gwt.validation.client.test.CyclicTestParent;
import com.google.gwt.validation.client.test.GroupSequenceClassTest;
import com.google.gwt.validation.client.test.ObjectGraphTest;
import com.google.gwt.validation.client.test.RecursiveValidationTest;
import com.google.gwt.validation.client.test.TestModel;
import com.google.gwt.validation.client.test.jsr303.Address1;
import com.google.gwt.validation.client.test.jsr303.Author;
import com.google.gwt.validation.client.test.jsr303.Book;

public class ServerValidatorTest {

	@Test
	public void testServerValidator() {
		//validate
		IValidator<TestModel> testModelValidator = new ServerValidator<TestModel>();
		
		//crate model
		TestModel tm = new TestModel();
		
		//validate 
		Set<InvalidConstraint<TestModel>> icSet = testModelValidator.validate(tm);

		//should be six, initially, from all groups
		assertEquals("Should be six invalid constraints, from the start", 6, icSet.size());
		
		//get from group one, should be three invalid constraints
		icSet = testModelValidator.validate(tm, "one");
		assertEquals("Should be three invalid constraints, from the start, from group one", 3, icSet.size());
	
		//specific property, from no group
		icSet = testModelValidator.validateProperty(tm, "date");
		assertEquals("Should be one invalid constraint, from the start, from 'date'", 1, icSet.size());
		
		//specific property from group one
		icSet = testModelValidator.validateProperty(tm, "date", "one");
		assertEquals("Should be one invalid constraint, from the start, from 'date', from group one", 1, icSet.size());

		//specific property from groups two and three
		icSet = testModelValidator.validateProperty(tm, "date", "two", "three");
		assertEquals("Should be one invalid constraint, from the start, from 'date', from groups two and three", 1, icSet.size());

		//specific property from group four
		icSet = testModelValidator.validateProperty(tm, "date", "four");
		assertEquals("Should be no invalid constraints, from the start, from 'date', from group four", 0, icSet.size());
		
		//set date to a non-null date
		tm.setDate(new Date());
		//all of the above tests should change -1, excepting the 0 test
		
		//should be six, initially, from all groups
		icSet = testModelValidator.validate(tm);
		assertEquals("Should be five invalid constraints, after date change", 5, icSet.size());
		
		//get from group one, should be three invalid constraints
		icSet = testModelValidator.validate(tm, "one");
		assertEquals("Should be two invalid constraints, after date change, from group one", 2, icSet.size());
	
		//specific property, from no group
		icSet = testModelValidator.validateProperty(tm, "date");
		assertEquals("Should be no invalid constraints, after date change, from 'date'", 0, icSet.size());
		
		//specific property from group one
		icSet = testModelValidator.validateProperty(tm, "date", "one");
		assertEquals("Should be no invalid constraints, after date change, from 'date', from group one", 0, icSet.size());

		//specific property from groups two and three
		icSet = testModelValidator.validateProperty(tm, "date", "two", "three");
		assertEquals("Should be no invalid constraints, after date change, from 'date', from groups two and three", 0, icSet.size());
	}
	
	@Test
	public void testClassLevel() {
		//annotated class instance
		AnnotatedSuperClass ac = new AnnotatedSuperClass();
		
		//get class level validator
		IValidator<AnnotatedSuperClass> validator = new ServerValidator<AnnotatedSuperClass>();
		
		//get invalid constraint
		Set<InvalidConstraint<AnnotatedSuperClass>> icSet = validator.validate(ac);
		
		//should be one
		assertEquals("Should be one invalid constraint.", 1, icSet.size());
		
		//fix name
		ac.setName("blah, blah");
		
		//get ic's
		icSet = validator.validate(ac);

		//should be none
		assertEquals("Should be no invalid constraints.", 0, icSet.size());		
	}	
	
	@Test
	public void testClassLevelWithFullInheritence() {
		//annotated class instance
		AnnotatedClass ac = new AnnotatedClass();
		
		//get class level validator
		IValidator<AnnotatedClass> validator = new ServerValidator<AnnotatedClass>();
		
		//get invalid constraint
		Set<InvalidConstraint<AnnotatedClass>> icSet = validator.validate(ac);
		
		//should be one
		assertEquals("Should be four invalid constraints.", 4, icSet.size());
		
		//fix name
		ac.setName("blah, blah");
		
		//get ic's
		icSet = validator.validate(ac);

		//should be none
		assertEquals("Should be no invalid constraints.", 0, icSet.size());		
	}	
	
	@Test
	public void testValidAnnotationOnClass() {
		//object graph thingy
		ObjectGraphTest ogt = new ObjectGraphTest();
		
		//get class level validator
		IValidator<ObjectGraphTest> validator = new ServerValidator<ObjectGraphTest>();
		
		//get invalid constraints
		Set<InvalidConstraint<ObjectGraphTest>> icSet = validator.validate(ogt);
		
		//should fail 4(one child) + 6(other child)
		assertEquals("Should be 54 invalid constraints", 54, icSet.size());
		
		//set the two values on the thing to null
		ogt.setAc(null);
		ogt.setTm(null);
		
		//re-validate
		icSet = validator.validate(ogt);
		
		//should only be two after nulls
		assertEquals("Should be 46 (not null) invalid constraints", 46, icSet.size());

		//put the valules back to not null
		ogt.setAc(new AnnotatedClass());
		ogt.setTm(new TestModel());
		
		//validate only one (4)
		icSet = validator.validateProperty(ogt, "ac");
		
		//assert
		assertEquals("Should be 4 from the AnnotattedClass", 4, icSet.size());
		
		//validate the other
		icSet = validator.validateProperty(ogt, "tm");
		
		//should be 6 from the TestModel
		assertEquals("Should be 6 from the TestModel", 6, icSet.size());
	}
	
	@Test
	public void testRecursive() {
		
		//recursive thingy
		RecursiveValidationTest rvt = new RecursiveValidationTest();
		rvt.setRvt(new RecursiveValidationTest());
		rvt.getRvt().setRvt(new RecursiveValidationTest());
		
		//get class level validator
		IValidator<RecursiveValidationTest> validator = new ServerValidator<RecursiveValidationTest>();
		
		//get invalid constraints
		Set<InvalidConstraint<RecursiveValidationTest>> icSet = validator.validate(rvt);
		
		//should be three things, one from each level
		assertEquals("Should be three not null constraints.", 3, icSet.size());		
	}
	
	@Test
	public void testCyclic() {
		
		//cyclic test class
		CyclicTestParent ctp = new CyclicTestParent();
		
		//get class level validator
		IValidator<CyclicTestParent> validator = new ServerValidator<CyclicTestParent>();
		
		//get invalid constraints
		Set<InvalidConstraint<CyclicTestParent>> icSet = validator.validate(ctp);
		
		//should be two things
		assertEquals("Should be two not null constraints.", 2, icSet.size());
	}
	
	@Test
	public void testGroupSequence() {
		
		//group test class
		GroupSequenceClassTest gsct = new GroupSequenceClassTest();
		
		//get class level validator
		IValidator<GroupSequenceClassTest> validator = new ServerValidator<GroupSequenceClassTest>();
		
		//get invalid constraints
		Set<InvalidConstraint<GroupSequenceClassTest>> icSet = validator.validate(gsct);
		
		//should be 1
		assertEquals("Should be one not null", 1, icSet.size());
		
		//set thing to not empty
		gsct.setNullString("");
		
		//revalidate
		icSet = validator.validate(gsct);
		
		//should be 1
		assertEquals("Should be one not empty", 1, icSet.size());
		
		//set thing to string
		gsct.setNullString("not empty");
		
		//revalidate
		icSet = validator.validate(gsct);
		
		//should be 1 not null from two.second;
		assertEquals("Should be one not null from two.first", 1, icSet.size());
	}
	
	@Test
	public void testJSR303_GroupSequenceExamples() {

		//create new author
		Author author = new Author();
		author.setLastName("Baudelaire");
		author.setFirstName("");
		Book book = new Book();
		book.setAuthor(author);
		
		//create validator for book
		IValidator<Book> validator = new ServerValidator<Book>();
		
		//should return
		//	NotEmpty failure on the title field
		Set<InvalidConstraint<Book>> icSet = validator.validate(book);
		
		//one
		assertEquals("Should be one invalid constraint.", 1, icSet.size());
		//not empty
		assertEquals("Not empty", "{constraint.notempty}", icSet.iterator().next().getMessage());
		
		//update
		book.setTitle("Les fleurs du mal");
		author.setCompany("Some random publisher with a very very very long name");
		
		//the first and second groups pass without failure, the last group undergoes validation
		icSet = validator.validate(book);
		
		//assert two failures
		assertEquals("Should be two invalid constraints.", 2, icSet.size());
		
		//contains a length, contains a notempty
		boolean length = false;
		boolean notempty = false;
		
		//check
		for(InvalidConstraint<Book> ic : icSet) {
			if(ic.getMessage().equals("{constraint.length}")) length = true;
			if(ic.getMessage().equals("{constraint.notempty}")) notempty = true;
			
		}
		
		//assert that both length and notempty are ture
		assertTrue("Length was found.",length);
		assertTrue("NotEmpty was found.",notempty);
		
	}
	
	@Test
	public void testJSR303_Address1Example() {
		
		//create address (#1 has no @Valid annotation and conforms to what is expected in JSR-303's example)
		Address1 address = new Address1();
		address.setAddressline1( null );
		address.setAddressline2( null );
		address.setCity("Llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch");
		//town in North Wales <- preserving the joke for posterity
		
		//create validator for address
		IValidator<Address1> validator = new ServerValidator<Address1>();
		
		//validate
		assertEquals("One for addressline1 violating NotNull, one for addressline2 violating NotNull and one for city violating Length.", 3, validator.validate(address).size());
		assertEquals("city violates Length and only city is validated", 1, validator.validateProperty(address, "city").size());
	}
}
