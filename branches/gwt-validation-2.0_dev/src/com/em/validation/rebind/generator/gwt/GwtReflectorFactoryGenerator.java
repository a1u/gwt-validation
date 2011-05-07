package com.em.validation.rebind.generator.gwt;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import com.em.validation.rebind.generator.source.ReflectorFactoryGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

public class GwtReflectorFactoryGenerator extends Generator {

	/**
	 * Stops the generation of a class from happening twice
	 * 
	 */
	private static Set<String> generationSet = new HashSet<String>();
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,	String typeName) throws UnableToCompleteException {
		
		ClassDescriptor factoryDescriptor = ReflectorFactoryGenerator.INSTANCE.getReflectorFactoryDescriptors();
		
		this.generateClass(factoryDescriptor,logger,context);		
		
		return factoryDescriptor.getFullClassName();
	}
	
	private void generateClass(ClassDescriptor descriptor, TreeLogger logger, GeneratorContext context) {
		//generate the class files for those that this class depends on
		for(ClassDescriptor depenency : descriptor.getDependencies()) {
			this.generateClass(depenency, logger, context);
		}

		//do not generate a class twice.  this hash set is used to manage that.
		if(GwtReflectorFactoryGenerator.generationSet.contains(descriptor.getFullClassName())) {
			return;
		}
		
		//create the print writer for the class
		PrintWriter printWriter = context.tryCreate(logger, descriptor.getPackageName(),descriptor.getClassName());
        //this is null when a class already exists.  in that case it will return null.  this happens when
        //an identical annotation is used twice
		if(printWriter == null) return;
        
		//use the print writer
		printWriter.print(descriptor.getClassContents());

		//commit the source
		context.commit(logger, printWriter);
		
		//once the source is committed successfully add to the hash set as generated
		GwtReflectorFactoryGenerator.generationSet.add(descriptor.getFullClassName());
	}
}
