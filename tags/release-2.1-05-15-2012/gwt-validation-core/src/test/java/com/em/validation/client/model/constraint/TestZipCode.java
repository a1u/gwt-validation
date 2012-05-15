package com.em.validation.client.model.constraint;

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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

@Pattern(regexp=".*")
@Size.List({
	@Size(min=2,max=400,message="TEST1"),
	@Size(max=5,min=-400,message="TEST2"),
	@Size(min=-5,max=10,message="TEST3")
})
@Size(min=6,max=6,message="TEST4")
@Constraint(validatedBy = {})
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestZipCode {
	
	String message() default "{com.em.validation.client.model.override.ZipCodeExample.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	@OverridesAttribute.List({
			@OverridesAttribute(constraint=Size.class,name="min",constraintIndex=0),
			@OverridesAttribute(constraint=Size.class,name="max",constraintIndex=1)
	})
	int size() default 5;
	
	@OverridesAttribute.List({
		@OverridesAttribute(constraint=Size.class,name="max",constraintIndex=2),
		@OverridesAttribute(constraint=Size.class,name="min",constraintIndex=2)
	})
	int otherSize() default 22;
	
	@OverridesAttribute(constraint=Pattern.class,name="regexp")
	String pattern() default "[0-9]*";
	
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		TestZipCode[] value();
	}
}
