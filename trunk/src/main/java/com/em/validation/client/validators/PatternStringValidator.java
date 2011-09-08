package com.em.validation.client.validators;

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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.em.validation.client.regex.RegexProvider;

public class PatternStringValidator implements ConstraintValidator<Pattern, String> {

	private String pattern = "";
	private Flag[] flags = new Flag[]{};
	
	@Override
	public void initialize(Pattern constraintAnnotation) {
		this.pattern = constraintAnnotation.regexp();
		this.flags = constraintAnnotation.flags();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null) return true;
		
		boolean result = RegexProvider.INSTANCE.matches(this.pattern, value, this.flags);
		
		return result;
	}

}
