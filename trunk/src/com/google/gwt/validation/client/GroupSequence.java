package com.google.gwt.validation.client;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Groups are useful to define subsets of constraints for validation purposes. Assigning constraints to groups provides
 * the ability to do partial validation to the group. The groups element of the constraint annotation specifies the
 * groups in which the constraint participates. At validation time, an optional parameter can be specified that narrows
 * the set of groups to be validated.
 * <br/>
 * One important application for grouping is to control the order of evaluation of constraints. There are often scenarios
 * where a preliminary set of constraints should be evaluated prior to other constraints. This is useful in two scenarios:
 * <ul>
 * <li>the second group depends on a stable state to run properly</li>
 * <li>the second group is a heavy consumer of time, CPU or memory and should be avoided if possible</li>
 * </ul>
 * <br/>
 * (Taken from the JSR-303 draft)
 * 
 * @author chris
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface GroupSequence {
	String name() default "";
	String[] sequence() default {};
}