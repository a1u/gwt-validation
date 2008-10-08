package com.google.gwt.validation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.google.gwt.validation.client.ClientTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	ClientTestSuite.class,
	BackEndTestSuite.class
})
public class ValidatorTestSuite {
	

}
