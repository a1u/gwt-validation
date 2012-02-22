package com.em.validation.suite.runtime;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.em.validation.client.core.defects.Defect_005;
import com.em.validation.client.core.defects.Defect_024;
import com.em.validation.client.core.defects.Defect_037;
import com.em.validation.client.core.defects.Defect_040;
import com.em.validation.client.core.defects.Defect_041;
import com.em.validation.client.core.defects.Defect_042;
import com.em.validation.client.core.defects.Defect_043;
import com.em.validation.client.core.defects.Defect_045;
import com.em.validation.client.core.defects.Defect_049;
import com.em.validation.client.core.defects.Defect_068;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Defect_005.class,
	Defect_024.class,
	Defect_037.class,
	Defect_040.class,
	Defect_041.class,
	Defect_042.class,
	Defect_043.class,
	Defect_045.class,
	Defect_049.class,
	Defect_068.class
})
public class DefectTestSuite {

}
