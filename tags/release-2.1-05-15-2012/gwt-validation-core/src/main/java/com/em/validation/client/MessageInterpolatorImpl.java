package com.em.validation.client;

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

import java.util.Locale;

import javax.validation.MessageInterpolator;

import com.em.validation.client.messages.MessageResolver;

public class MessageInterpolatorImpl implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
		
		return this.interpolate(messageTemplate, context, Locale.getDefault());
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		MessageResolver resolver = MessageResolver.INSTANCE;
		
		String template = messageTemplate;
		String output = template;
		
		//only need to get the localized version if and when the string starts and ends with { and } with no { or } in between.
		//"{java...thing.Class.message}" is localized
		//"{attribute} of {property} with {property}" is not localized
		if(template.startsWith("{") && template.endsWith("}") && !template.substring(1,template.length() - 1).contains("{") && !template.substring(1,template.length() - 1).contains("}")) {
			 output = resolver.getLocalizedMessageTemplate(template);
		}

		output = resolver.getMessage(output, context.getConstraintDescriptor().getAttributes());			
		
		return output;
	}

}
