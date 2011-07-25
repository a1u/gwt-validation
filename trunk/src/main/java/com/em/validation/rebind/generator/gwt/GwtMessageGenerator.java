package com.em.validation.rebind.generator.gwt;

import com.em.validation.rebind.generator.source.MessageGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

public class GwtMessageGenerator extends GwtGenerator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,	String typeName) throws UnableToCompleteException {
		ClassDescriptor messageDescriptor = MessageGenerator.INSTANCE.generateMessage();
		
		this.generateClass(messageDescriptor,logger,context);	
		
		this.reset();
		
		return messageDescriptor.getFullClassName();
	}

}
