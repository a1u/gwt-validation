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

//Taken in part from: http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/src/com/google/gwt/i18n/rebind/LocalizableGenerator.java

/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.em.validation.rebind.generator.source.MessageGenerator;
import com.em.validation.rebind.metadata.ClassDescriptor;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.i18n.rebind.LocaleUtils;
import com.google.gwt.i18n.shared.GwtLocale;

public class GwtMessageGenerator extends GwtGenerator {
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,	String typeName) throws UnableToCompleteException {
		//get locale
		GwtLocale current = this.getCurrentGenerationLocale(logger, context);		
		
		ClassDescriptor messageDescriptor = MessageGenerator.INSTANCE.generateMessage(current);
		
		this.generateClass(messageDescriptor,logger,context);	
		
		this.reset();
		
		return messageDescriptor.getFullClassName();
	}

	/**
	 * Get the locale that is currently being generated
	 * <br/><br/>
	 * taken from http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/src/com/google/gwt/i18n/rebind/LocalizableGenerator.java
	 * 
	 * @param logger
	 * @param context
	 * @return
	 */
	public GwtLocale getCurrentGenerationLocale(TreeLogger logger, GeneratorContext context) {
	    // Get the current locale
	    PropertyOracle propertyOracle = context.getPropertyOracle();
	    LocaleUtils localeUtils = LocaleUtils.getInstance(logger, propertyOracle, context);
	    GwtLocale locale = localeUtils.getCompileLocale();
	    return locale;	    
	}
}
