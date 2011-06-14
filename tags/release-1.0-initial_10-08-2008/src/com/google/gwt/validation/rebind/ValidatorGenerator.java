package com.google.gwt.validation.rebind;

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
