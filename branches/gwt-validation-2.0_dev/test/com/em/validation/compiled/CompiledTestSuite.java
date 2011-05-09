package com.em.validation.compiled;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.compiled.cases.ConstraintsTest;
import com.em.validation.compiled.cases.MetadataTest;
import com.em.validation.compiled.cases.defects.DefectSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DefectSuite.class,
	ConstraintsTest.class,
	MetadataTest.class
})
public class CompiledTestSuite {

}
