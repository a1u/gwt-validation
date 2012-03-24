package com.em.validation.client.model.generic;

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

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.em.validation.client.model.constraint.FakeConstraint;

public class TestClass extends ParentClass implements TestInterface, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="NOT NULL 1")
	@Pattern.List( {
		@Pattern(regexp=".+"),
		@Pattern(regexp="--")
	})
	@FakeConstraint
	@Size(min=2, max=12)
	public String publicTestString = "publicTestString";
	
	@Min(4)
	public int publicTestInt = 0;
	
	@Size(min=3,max=15)
	private String testString = "testString";
	
	private int testInt = 0;

	@NotNull(message="NOT NULL 2")
	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	@Max(10)
	@Min(5)
	@FakeConstraint
	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}

	@Size(min=1)
	@Override
	public String getTestInterfaceString() {
		return "testInterfaceString";
	}

	@Size(min=2)
	@Override
	public String getParentAbstractString() {
		return "parentAbstractString";
	}
	
}
