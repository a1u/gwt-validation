package com.em.validation.client.model.constraint;

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
