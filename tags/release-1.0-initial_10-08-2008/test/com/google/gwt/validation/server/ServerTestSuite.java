package com.google.gwt.validation.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ServerValidatorTest.class,
	AssertFalseServerTest.class,
	AssertTrueServerTest.class,
	EmailServerTest.class,
	FutureServerTest.class,
	LengthServerTest.class,
	MaxServerTest.class,
	MinServerTest.class,
	NotEmptyServerTest.class,
	NotNullServerTest.class,
	PastServerTest.class,
	PatternServerTest.class,
	RangeServerTest.class,
	SizeServerTest.class
})
public class ServerTestSuite {

}
