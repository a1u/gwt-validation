package com.em.validation.rebind.template;

/*
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
