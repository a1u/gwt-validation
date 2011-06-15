package ${targetPackage};

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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ValidationException;

public class GeneratedConstraintValidatorFactory implements ConstraintValidatorFactory {
	
	public GeneratedConstraintValidatorFactory() {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		
		T cvInstance = null;
		
		//=====================================================
		// return new instances of constraint validator classes
		// this doesn't use the same pattern as other factories
		// because it is not (cannot be) cached.  if it were 
		// to be cached then the initialize state would get 
		// munged from each time the cached instance would be 
		// returned and nothing would work properly.
		//=====================================================
		
		<#list constraintValidators as validator>
		//${validator}
		<#if validator_index &gt; 0>else </#if>if(${validator}.class.equals(key)) {
			cvInstance = (T)new ${validator}(); 
		}
		</#list>
				
		if(cvInstance == null) {
			throw new ValidationException("The ConstraintValidator of type " + key.getName() + " was not available as a constraint at code generation time.");
		}
		
		return cvInstance;
	}
}
