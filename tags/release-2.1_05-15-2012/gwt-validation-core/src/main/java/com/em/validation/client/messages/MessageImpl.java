package com.em.validation.client.messages;

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

import java.util.HashMap;
import java.util.Map;

public abstract class MessageImpl implements IMessage {

	private Map<String, String> localeMap = new HashMap<String, String>();
	
	protected void addString(String key, String value) {
		//put key and value into locale map
		this.localeMap.put(key,value);
	}
	
	@Override
	public String getString(String key) {
		return this.localeMap.get(key);
	}	
}
