package com.em.validation.rebind.template;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class StringHashMethod implements TemplateMethodModel {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {

		if(args.size() != 1) {
			throw new TemplateModelException("Hash requires 1 argument");
		}
		
		//get string
		String toHash = (String)args.get(0);
		//get hash code as string
		return String.valueOf(toHash.hashCode());
	}

}
