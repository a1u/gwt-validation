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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class ParentClass implements ParentInterface {

	@NotNull(message="NOT NULL PARENT")
	@Size(min=4)
	public String publicParentString = "publicParentString";
	
	@Max(22)
	private int parentInt = 0;

	@Min(0)
	public int getParentInt() {
		return parentInt;
	}

	public void setParentInt(int parentInt) {
		this.parentInt = parentInt;
	}

	@Override
	@Max(400)
	public int getParentInterfaceInt() {
		return 0;
	}	
	
	@NotNull
	public abstract String getParentAbstractString();
}
