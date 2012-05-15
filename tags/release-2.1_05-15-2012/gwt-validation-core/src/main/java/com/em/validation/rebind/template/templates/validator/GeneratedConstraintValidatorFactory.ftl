package ${targetPackage};

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
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
