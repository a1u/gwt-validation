package com.em.validation.reflective;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.reflective.cases.ConstraintsTest;
import com.em.validation.reflective.cases.MetadataTest;
import com.em.validation.reflective.cases.defects.DefectSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DefectSuite.class,
	ConstraintsTest.class,
	MetadataTest.class
})
public class ReflectiveTestSuite {

}
