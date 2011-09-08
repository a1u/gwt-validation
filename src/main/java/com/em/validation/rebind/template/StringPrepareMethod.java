package com.em.validation.rebind.template;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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


import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class StringPrepareMethod implements TemplateMethodModel {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {

		if(args.size() != 1) {
			throw new TemplateModelException("Prepare requires 1 argument");
		}
		
		//get string
		String toPrepare = (String)args.get(0);
		
		//remove the \" from the begining
		if(toPrepare.startsWith("\"")) {
			toPrepare = toPrepare.substring(1);
		}
		
		//remove the \" from the end
		if(toPrepare.endsWith("\"")) {
			toPrepare = toPrepare.substring(0,toPrepare.length()-1);
		}

		return toPrepare;
	}	
	
}
