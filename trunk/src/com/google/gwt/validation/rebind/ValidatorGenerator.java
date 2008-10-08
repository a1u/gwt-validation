package com.google.gwt.validation.rebind;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * Generates the classes needed to validate a type that is marked as validatable (<code>com.google.gwt.validator.client.interfaces.IValidatable</code>) 
 *
 * @author chris
 * 
 * @see com.google.gwt.validation.client.interfaces.IValidatable
 */
public class ValidatorGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,	String typeName) throws UnableToCompleteException {

		//get the type oracle
		TypeOracle typeOracle = context.getTypeOracle();
		
		//assert that the type oracle is not null
		assert(typeOracle != null);
		
		//get type
		JClassType vType = typeOracle.findType(typeName);
		
		//if vtype is null (the type incoming)
		if(vType == null) {
			logger.log(TreeLogger.ERROR, "Type oracle unable to find type for " + typeName + ".");
			throw new UnableToCompleteException();
		}
		
		ValidatorCreator validatorCreator = new ValidatorCreator(logger, context, typeName);
		
		String className = validatorCreator.createValidatorImplementation();
		
		//return generated classname
		return className;
	}

	
	
}
