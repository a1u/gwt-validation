package com.google.gwt.validation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.gwt.validation.rebind.RebindTestSuite;
import com.google.gwt.validation.server.ServerTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	RebindTestSuite.class,
	ServerTestSuite.class
})
public class BackEndTestSuite {

}
