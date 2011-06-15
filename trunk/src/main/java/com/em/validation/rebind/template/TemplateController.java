package com.em.validation.rebind.template;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
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
