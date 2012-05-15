package com.em.validation.rebind.generator.gwt;

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

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import com.em.validation.rebind.generator.GeneratorState;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;

public abstract class GwtGenerator extends Generator {
	
	/**
	 * Stops the generation of a class from happening twice
	 * 
	 */
	private Set<String> generatedSet = new HashSet<String>();
	
	/**
	 * Actually generate all of the files described by the ClassDescriptor and the dependency tree.
	 * 
	 * @param descriptor
	 * @param logger
	 * @param context
	 */
	protected void generateClass(ClassDescriptor descriptor, TreeLogger logger, GeneratorContext context) {
		//set using gwt features in generator state
		GeneratorState.INSTANCE.setUsingGwtFeatures(true);		
		
		//do not generate a class twice.  this hash set is used to manage that.
		if(this.generatedSet.contains(descriptor.getFullClassName())) {
			return;
		}

		//create the print writer for the class
		PrintWriter printWriter = context.tryCreate(logger, descriptor.getPackageName(),descriptor.getClassName());
        //this is null when a class already exists. this usually happens when the print writer would be attempting
		//to output the same class twice.  with the generationSet and various other blocks this should not happen.
		if(printWriter == null) {
			return;
		}
		
		//log generation of the code, debug
		logger.log(Type.DEBUG, "Generating: " + descriptor.getPackageName() + "." + descriptor.getClassName());
        
		//use the print writer
		printWriter.print(descriptor.getClassContents());

		//commit the source
		context.commit(logger, printWriter);
		
		//if not in the hash set, then set it to prevent cyclical / infinite recursion problems
		this.generatedSet.add(descriptor.getFullClassName());
		
		//generate the class files for those that this class depends on
		for(ClassDescriptor depenency : descriptor.getDependencies()) {
			this.generateClass(depenency, logger, context);
		}
	}
	
	protected void reset() {
		this.generatedSet.clear();
	}
}
