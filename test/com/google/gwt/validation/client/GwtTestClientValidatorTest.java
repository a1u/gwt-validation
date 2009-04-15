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
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.validation.client.interfaces.IValidator;
import com.google.gwt.validation.client.test.AnnotatedClass;
import com.google.gwt.validation.client.test.AnnotatedSuperClass;
import com.google.gwt.validation.client.test.CyclicTestParent;
import com.google.gwt.validation.client.test.GroupSequenceClassTest;
import com.google.gwt.validation.client.test.ObjectGraphTest;
import com.google.gwt.validation.client.test.ParametrizedTypes;
import com.google.gwt.validation.client.test.RecursiveValidationTest;
import com.google.gwt.validation.client.test.SimpleClass;
import com.google.gwt.validation.client.test.TestModel;
import com.google.gwt.validation.client.test.jsr303.Address1;
import com.google.gwt.validation.client.test.jsr303.Author;
import com.google.gwt.validation.client.test.jsr303.Book;

public class GwtTestClientValidatorTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.google.gwt.validation.Validation";
    }

    @Test
    public void testTestModel() {

        // validate
        final IValidator<TestModel> testModelValidator = GWT.create(TestModel.class);

        // crate model
        final TestModel tm = new TestModel();

        // validate
        Set<InvalidConstraint<TestModel>> icSet = testModelValidator.validate(tm);

        // should be six, initially, from all groups
        assertEquals("Should be six invalid constraints, from the start", 6, icSet.size());

        // get from group one, should be three invalid constraints
        icSet = testModelValidator.validate(tm, "one");
        assertEquals("Should be three invalid constraints, from the start, from group one", 3, icSet.size());

        // specific property, from no group
        icSet = testModelValidator.validateProperty(tm, "date");
        assertEquals("Should be one invalid constraint, from the start, from 'date'", 1, icSet.size());

        // specific property from group one
        icSet = testModelValidator.validateProperty(tm, "date", "one");
        assertEquals("Should be one invalid constraint, from the start, from 'date', from group one", 1, icSet.size());

        // specific property from groups two and three
        icSet = testModelValidator.validateProperty(tm, "date", "two", "three");
        assertEquals("Should be one invalid constraint, from the start, from 'date', from groups two and three", 1, icSet.size());

        // specific property from group four
        icSet = testModelValidator.validateProperty(tm, "date", "four");
        assertEquals("Should be no invalid constraints, from the start, from 'date', from group four", 0, icSet.size());

        // set date to a non-null date
        tm.setDate(new Date());
        // all of the above tests should change -1, excepting the 0 test

        // should be six, initially, from all groups
        icSet = testModelValidator.validate(tm);
        assertEquals("Should be five invalid constraints, after date change", 5, icSet.size());

        // get from group one, should be three invalid constraints
        icSet = testModelValidator.validate(tm, "one");
        assertEquals("Should be two invalid constraints, after date change, from group one", 2, icSet.size());

        // specific property, from no group
        icSet = testModelValidator.validateProperty(tm, "date");
        assertEquals("Should be no invalid constraints, after date change, from 'date'", 0, icSet.size());

        // specific property from group one
        icSet = testModelValidator.validateProperty(tm, "date", "one");
        assertEquals("Should be no invalid constraints, after date change, from 'date', from group one", 0, icSet.size());

        // specific property from groups two and three
        icSet = testModelValidator.validateProperty(tm, "date", "two", "three");
        assertEquals("Should be no invalid constraints, after date change, from 'date', from groups two and three", 0, icSet.size());

    }

    public void testClassLevel() {
        // null class instance
        final AnnotatedSuperClass ac = new AnnotatedSuperClass();

        // get class level validator
        final IValidator<AnnotatedSuperClass> validator = GWT.create(AnnotatedSuperClass.class);

        // get invalid constraint
        Set<InvalidConstraint<AnnotatedSuperClass>> icSet = validator.validate(ac);

        // should be one
        assertEquals("Should be one invalid constraint.", 1, icSet.size());

        // fix name
        ac.setName("blah, blah");

        // get ic's
        icSet = validator.validate(ac);

        // should be none
        assertEquals("Should be no invalid constraints.", 0, icSet.size());
    }

    public void testClassLevelWithFullInheritence() {
        // null class instance
        final AnnotatedClass ac = new AnnotatedClass();

        // get class level validator
        final IValidator<AnnotatedClass> validator = GWT.create(AnnotatedClass.class);

        // get invalid constraint
        Set<InvalidConstraint<AnnotatedClass>> icSet = validator.validate(ac);

        // should be one
        assertEquals("Should be four invalid constraints.", 4, icSet.size());

        // fix name
        ac.setName("blah, blah");

        // get ic's
        icSet = validator.validate(ac);

        // should be none
        assertEquals("Should be no invalid constraints.", 0, icSet.size());
    }

    public void testValidAnnotationOnClass() {
        // object graph thingy
        final ObjectGraphTest ogt = new ObjectGraphTest();

        // get class level validator
        final IValidator<ObjectGraphTest> validator = GWT.create(ObjectGraphTest.class);

        // get invalid constraints
        Set<InvalidConstraint<ObjectGraphTest>> icSet = validator.validate(ogt);

        // should fail 4(one child) + 6(other child)
        assertEquals("Should be 54 invalid constraints", 54, icSet.size());

        // set the two values on the thing to null
        ogt.setAc(null);
        ogt.setTm(null);

        // re-validate
        icSet = validator.validate(ogt);

        // should only be two after nulls
        assertEquals("Should be 46 (not null) invalid constraints", 46, icSet.size());

        // put the valules back to not null
        ogt.setAc(new AnnotatedClass());
        ogt.setTm(new TestModel());

        // validate only one (4)
        icSet = validator.validateProperty(ogt, "ac");

        // assert
        assertEquals("Should be 4 from the AnnotattedClass", 4, icSet.size());

        // validate the other
        icSet = validator.validateProperty(ogt, "tm");

        // should be 6 from the TestModel
        assertEquals("Should be 6 from the TestModel", 6, icSet.size());
    }

    public void testRecursive() {

        // recursive thingy
        final RecursiveValidationTest rvt = new RecursiveValidationTest();
        rvt.setRvt(new RecursiveValidationTest());
        rvt.getRvt().setRvt(new RecursiveValidationTest());

        // get class level validator
        final IValidator<RecursiveValidationTest> validator = GWT.create(RecursiveValidationTest.class);

        // get invalid constraints
        final Set<InvalidConstraint<RecursiveValidationTest>> icSet = validator.validate(rvt);

        // should recursively descend for 3
        assertEquals("Should be three not null constraints.", 3, icSet.size());
    }

    public void testCyclic() {

        // cyclic test class
        final CyclicTestParent ctp = new CyclicTestParent();

        // get class level validator
        final IValidator<CyclicTestParent> validator = GWT.create(CyclicTestParent.class);

        // get invalid constraints
        final Set<InvalidConstraint<CyclicTestParent>> icSet = validator.validate(ctp);

        // should be two things
        assertEquals("Should be two not null constraints.", 2, icSet.size());
    }

    public void testGroupSequence() {

        // group test class
        final GroupSequenceClassTest gsct = new GroupSequenceClassTest();

        // get class level validator
        final IValidator<GroupSequenceClassTest> validator = GWT.create(GroupSequenceClassTest.class);

        // get invalid constraints
        Set<InvalidConstraint<GroupSequenceClassTest>> icSet = validator.validate(gsct);

        // should be 1
        assertEquals("Should be one not null", 1, icSet.size());

        // set thing to not empty
        gsct.setNullString("");

        // revalidate
        icSet = validator.validate(gsct);

        // should be 1
        assertEquals("Should be one not empty", 1, icSet.size());

        // set thing to string
        gsct.setNullString("not empty");

        // revalidate
        icSet = validator.validate(gsct);

        // should be 1 not null from two.second;
        assertEquals("Should be one not null from two.first", 1, icSet.size());
    }

    public void testJSR303_GroupSequenceExamples() {

        // create new author
        final Author author = new Author();
        author.setLastName("Baudelaire");
        author.setFirstName("");
        final Book book = new Book();
        book.setAuthor(author);

        // create validator for book
        final IValidator<Book> validator = GWT.create(Book.class);

        // should return
        // NotEmpty failure on the title field
        Set<InvalidConstraint<Book>> icSet = validator.validate(book);

        // one
        assertEquals("Should be one invalid constraint.", 1, icSet.size());
        // not empty
        assertEquals("Not empty", "{constraint.notempty}", icSet.iterator().next().getMessage());

        // update
        book.setTitle("Les fleurs du mal");
        author.setCompany("Some random publisher with a very very very long name");

        // the first and second groups pass without failure, the last group
        // undergoes validation
        icSet = validator.validate(book);

        // assert two failures
        assertEquals("Should be two invalid constraints.", 2, icSet.size());

        // contains a length, contains a notempty
        boolean length = false;
        boolean notempty = false;

        // check
        for (final InvalidConstraint<Book> ic : icSet) {
            if (ic.getMessage().equals("{constraint.length}"))
                length = true;
            if (ic.getMessage().equals("{constraint.notempty}"))
                notempty = true;

        }

        // assert that both length and notempty are ture
        assertTrue("Length was found.", length);
        assertTrue("NotEmpty was found.", notempty);

    }

    public void testJSR303_Address1Example() {

        // create address (#1 has no @Valid annotation and conforms to what is
        // expected in JSR-303's example)
        final Address1 address = new Address1();
        address.setAddressline1(null);
        address.setAddressline2(null);
        address.setCity("Llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch");
        // town in North Wales <- preserving the joke for posterity

        // create validator for address
        final IValidator<Address1> validator = GWT.create(Address1.class);

        // validate
        assertEquals("One for addressline1 violating NotNull, one for addressline2 violating NotNull and one for city violating Length.", 3, validator.validate(address).size());
        assertEquals("city violates Length and only city is validated", 1, validator.validateProperty(address, "city").size());

        // no validate value as of yet
    }

    @Test
    public void emptyTest() {
        // so that junit won't complain about an empty test
    }

    public void testInvalidConstraintPath() {

        // object graph thingy
        final ObjectGraphTest ogt = new ObjectGraphTest();

        // get class level validator
        final IValidator<ObjectGraphTest> validator = GWT.create(ObjectGraphTest.class);

        // get invalid constraints
        final Set<InvalidConstraint<ObjectGraphTest>> icSet = validator.validate(ogt);

        // should fail 4(one child) + 6(other child)
        assertEquals("Should be 54 invalid constraints", 54, icSet.size());

        // build map of invalid constraints paths and its count
        final Map<String, Integer> icPaths = new TreeMap<String, Integer>();
        for (final InvalidConstraint<ObjectGraphTest> invalidConstraint : icSet) {
            Integer count = icPaths.get(invalidConstraint.getPropertyPath());

            // if new path set count to 0
            if (count == null) {
                count = 0;
            }

            // increase path count
            count += 1;

            // put current count into map
            icPaths.put(invalidConstraint.getPropertyPath(), count);
        }

        // prepare testable strings - '<path> (<count>)'
        final Set<String> testablePaths = new TreeSet<String>();
        for (final String path : icPaths.keySet()) {
            testablePaths.add(path + " (" + icPaths.get(path) + ")");
        }

        // define test patterns - '<path> (<count>)'
        final String[] testPatterns = { "ac (4)", "acList[0] (4)", "acList[1] (4)", "acList[2] (4)", "acSet[0] (4)", "acSet[1] (4)", "tm.date (1)", "tm.failFutureDate (1)", "tm.notInFuture (1)", "tm.shouldBeFalse (1)", "tm.shouldBeTrue (1)", "tm.shouldNotBeEmpty (1)", "tmArray[0].date (1)",
                "tmArray[0].failFutureDate (1)", "tmArray[0].notInFuture (1)", "tmArray[0].shouldBeFalse (1)", "tmArray[0].shouldBeTrue (1)", "tmArray[0].shouldNotBeEmpty (1)", "tmArray[1].date (1)", "tmArray[1].failFutureDate (1)", "tmArray[1].notInFuture (1)", "tmArray[1].shouldBeFalse (1)",
                "tmArray[1].shouldBeTrue (1)", "tmArray[1].shouldNotBeEmpty (1)", "tmMap[\"string1\"].date (1)", "tmMap[\"string1\"].failFutureDate (1)", "tmMap[\"string1\"].notInFuture (1)", "tmMap[\"string1\"].shouldBeFalse (1)", "tmMap[\"string1\"].shouldBeTrue (1)",
                "tmMap[\"string1\"].shouldNotBeEmpty (1)", "tmMap[\"string2\"].date (1)", "tmMap[\"string2\"].failFutureDate (1)", "tmMap[\"string2\"].notInFuture (1)", "tmMap[\"string2\"].shouldBeFalse (1)", "tmMap[\"string2\"].shouldBeTrue (1)", "tmMap[\"string2\"].shouldNotBeEmpty (1)" };

        // sort patterns strings
        final Set<String> patternsPaths = new TreeSet<String>(Arrays.asList(testPatterns));

        // all paths should be the same
        assertTrue("All paths should be equals.", patternsPaths.equals(testablePaths));

    }

    public void testParametrizedTypes() {
        //create simple hierarchy
        final ParametrizedTypes obj = new ParametrizedTypes();
        final ArrayList<SimpleClass> list = new ArrayList<SimpleClass>();
        list.add(new SimpleClass());
        list.add(new SimpleClass());
        obj.setParametrizedCollection(list);

        //create validator
        final IValidator<ParametrizedTypes> validator = GWT.create(ParametrizedTypes.class);

        // get invalid constraints
        final Set<InvalidConstraint<ParametrizedTypes>> icSet = validator.validate(obj);

        assertEquals("Should be 4 not null invalid constraints.", 4, icSet.size());

    }

}
