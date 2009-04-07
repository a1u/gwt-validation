package com.google.gwt.validation.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	GwtTestAbstractValidatorTest.class,
	GwtTestAssertFalseTest.class,
	GwtTestAssertTrueTest.class,
	GwtTestClientValidatorTest.class,
	GwtTestLengthTest.class,
	GwtTestMaxTest.class,
	GwtTestMinTest.class,
	GwtTestNotEmptyTest.class,
	GwtTestNotNullTest.class,
	GwtTestRangeTest.class,
	GwtTestPatternTest.class,
	GwtTestPastTest.class,
	GwtTestFutureTest.class,
	GwtTestSizeTest.class,
	GwtTestEmailTest.class,
	GwtTestCodeMuseumTest.class
})
public class ClientTestSuite {

}
