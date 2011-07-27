package com.em.validation.rebind.generator.gwt;

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

import com.em.validation.rebind.generator.source.AnnotationInstanceFactoryGenerator;
import com.em.validation.rebind.generator.source.ConstraintValidatorFactoryGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

public class GwtConstraintValidatorFactoryGenerator extends GwtGenerator {
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,	String typeName) throws UnableToCompleteException {
		
		ClassDescriptor factoryDescriptor = ConstraintValidatorFactoryGenerator.INSTANCE.generateConstraintValidatorFactory();
		
		this.generateClass(factoryDescriptor,logger,context);	
		
		//reset this
		this.reset();
		
		//reset the related factories
		AnnotationInstanceFactoryGenerator.INSTANCE.clear();
		
		return factoryDescriptor.getFullClassName();
	}
	
}
