package com.em.validation.client.model.sequence;

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

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import com.em.validation.client.constraints.NotEmpty;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.MaxGroup;

@GroupSequence({BasicGroup.class, ExtendedGroup.class, MaxGroup.class})
public class ClassWithSequence {

	@NotEmpty(groups=BasicGroup.class)
	private String notEmpty = "12;";
	
	@AssertTrue(groups=ExtendedGroup.class)
	private boolean shouldBeTrue = true;
	
	@AssertFalse(groups=MaxGroup.class)
	private boolean shouldBeFalse = false;

	public String getNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(String notEmpty) {
		this.notEmpty = notEmpty;
	}

	public boolean isShouldBeTrue() {
		return shouldBeTrue;
	}

	public void setShouldBeTrue(boolean shouldBeTrue) {
		this.shouldBeTrue = shouldBeTrue;
	}

	public boolean isShouldBeFalse() {
		return shouldBeFalse;
	}

	public void setShouldBeFalse(boolean shouldBeFalse) {
		this.shouldBeFalse = shouldBeFalse;
	}	
	
}
