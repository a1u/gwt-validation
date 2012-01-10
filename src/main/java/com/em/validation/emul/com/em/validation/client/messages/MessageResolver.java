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

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.em.validation.client.messages.IMessage;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.LocaleInfo;

public enum MessageResolver implements IMessageResolver {

	INSTANCE;
	
	private IMessage gwtLocalizedMessage = null;
	
	private MessageResolver() {
		this.gwtLocalizedMessage = GWT.create(IMessage.class);
	}
	
	@Override
	public String getLocalizedMessageTemplate(String message) {
		String template = message;

		String output = message;
		
		if(template.startsWith("{")) {
			template = template.substring(1);
		}
		
		if(template.endsWith("}")) {
			template = template.substring(0,template.length() - 1);
		}	
		
		try {
			output = this.gwtLocalizedMessage.getString(template);
		} catch(MissingResourceException mex) {
			mex.printStackTrace();
			output = message;
		}
		if(output == null || output.isEmpty()) {
			output = message;
		}
		return output;
	}

	@Override
	public String getMessage(String messageTemplate, Map<String, Object> properties) {
		return MessageUtil.getMessage(messageTemplate, properties);
	}
	
}
