package com.em.validation.suite.client;

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

import com.em.validation.client.gwt.defects.GwtDefect_005;
import com.em.validation.client.gwt.defects.GwtDefect_024;
import com.em.validation.client.gwt.defects.GwtDefect_037;
import com.em.validation.client.gwt.defects.GwtDefect_040;
import com.em.validation.client.gwt.defects.GwtDefect_041;
import com.em.validation.client.gwt.defects.GwtDefect_042;
import com.em.validation.client.gwt.defects.GwtDefect_043;
import com.em.validation.client.gwt.defects.GwtDefect_045;
import com.em.validation.client.gwt.defects.GwtDefect_049;
import com.em.validation.client.gwt.defects.GwtDefect_068;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GwtDefect_005.class,
	GwtDefect_024.class,
	GwtDefect_037.class,
	GwtDefect_040.class,
	GwtDefect_041.class,
	GwtDefect_042.class,
	GwtDefect_043.class,
	GwtDefect_045.class,
	GwtDefect_049.class,
	GwtDefect_068.class
})
public class GwtDefectTestSuite {

}
