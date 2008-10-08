package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the field or method is not empty when validated.  
 * <br/>
 * It also asserts that the field or method is not null.
 * <br/>
 * If this behavior seems contrary to the normal pattern of returning an invalid constraint when the 
 * value is null this is because I've had this discussion with members of the JSR-303 pannel and they've 
 * assured me that this is the intended behavior for <code>@NotEmpty</code.>
 * <br/>
 * Further it can be seen as the composition of:
 * <ul>
 * 	<li><code>@NotNull</code></li>
 * 	<li><code>@Length(minimum=1)</code></li>
 * </ul>
 *  
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(NotEmptyValidator.class)
@Retention(RUNTIME)
public @interface NotEmpty {
	
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.notempty}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};

}
