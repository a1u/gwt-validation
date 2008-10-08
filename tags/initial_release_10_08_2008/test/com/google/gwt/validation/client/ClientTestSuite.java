package com.google.gwt.validation.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AbstractValidatorTest.class,
	AssertFalseTest.class,
	AssertTrueTest.class,
	ClientValidatorTest.class,
	LengthTest.class,
	MaxTest.class,
	MinTest.class,
	NotEmptyTest.class,
	NotNullTest.class,
	RangeTest.class,
	PatternTest.class,
	PastTest.class,
	FutureTest.class,
	SizeTest.class,
	EmailTest.class
})
public class ClientTestSuite {

}
