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

	/**
	 * Actually generate all of the files described by the ClassDescriptor and the dependency tree.
	 * 
	 * @param descriptor
	 * @param logger
	 * @param context
	 */
	private void generateClass(ClassDescriptor descriptor, TreeLogger logger, GeneratorContext context) {
		//do not generate a class twice.  this hash set is used to manage that.
		if(GwtReflectorFactoryGenerator.generationSet.contains(descriptor.getFullClassName())) {
			return;
		}

		//create the print writer for the class
		PrintWriter printWriter = context.tryCreate(logger, descriptor.getPackageName(),descriptor.getClassName());
        //this is null when a class already exists. this usually happens when the print writer would be attempting
		//to output the same class twice.  with the generationSet and various other blocks this should not happen.
		if(printWriter == null) return;
        
		//use the print writer
		printWriter.print(descriptor.getClassContents());

		//commit the source
		context.commit(logger, printWriter);
		
		//if not in the hash set, then set it to prevent cyclical / infinite recursion problems
		GwtReflectorFactoryGenerator.generationSet.add(descriptor.getFullClassName());
		
		//generate the class files for those that this class depends on
		for(ClassDescriptor depenency : descriptor.getDependencies()) {
			this.generateClass(depenency, logger, context);
		}
	}
}
