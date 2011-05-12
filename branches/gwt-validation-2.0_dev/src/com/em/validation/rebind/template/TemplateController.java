package com.em.validation.rebind.template;

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
