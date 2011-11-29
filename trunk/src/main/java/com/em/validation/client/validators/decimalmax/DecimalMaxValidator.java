package com.em.validation.client.validators.decimalmax;

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
import javax.validation.constraints.DecimalMax;

import com.em.validation.client.format.NumberFormatProvider;

public abstract class DecimalMaxValidator<T> implements ConstraintValidator<DecimalMax,T> {

	protected double maxValue = 0;
	protected String maxValueString = "0";

	@Override
	public void initialize(DecimalMax constraintAnnotation) {
		
		String stringValue = constraintAnnotation.value(); 
		
		try {
			this.maxValue = NumberFormatProvider.INSTANCE.getDoubleFromString(stringValue);
		} catch (Exception ex) {
			//no valid max value from string
		}
		
		this.maxValueString = String.valueOf(this.maxValue);
	}
	
}
