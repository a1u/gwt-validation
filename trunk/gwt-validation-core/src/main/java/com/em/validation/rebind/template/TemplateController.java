package com.em.validation.rebind.template;

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

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.em.validation.rebind.generator.GeneratorState;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Lazy singleton that provides the FreeMarker configuration object.
 * 
 * @author chris
 *
 */
public enum TemplateController {

	INSTANCE;
	
	private Configuration freeConfig = null;
		
	private TemplateController() {
		Configuration freemarkerConfig = new Configuration();
		freemarkerConfig.setClassForTemplateLoading(TemplateController.class, "");
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
		
		//set freemarker configuration instance
		this.freeConfig = freemarkerConfig;
	}
	
	public String processTemplate(String templateName, Map<String, Object> templateModel) {
		//create empty byte array output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		//add hash method to map
		templateModel.put("hash", new StringHashMethod());
		templateModel.put("prep", new StringPrepareMethod());
		
		//add the template value that sets up using gwt features for code splitting and others
		templateModel.put("usingGwtFeatures",GeneratorState.INSTANCE.isUsingGwtFeatures());
		
		//process template
		try {
			Writer outputWriter = new OutputStreamWriter(outputStream);
			
			Template template = this.freeConfig.getTemplate(templateName);
			template.process(templateModel, outputWriter);
			
			outputWriter.flush();			
			outputWriter.close();			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return outputStream.toString();
	}
	
}
