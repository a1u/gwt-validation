// $Id: MessageInterpolator.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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
package javax.validation;

import javax.validation.metadata.ConstraintDescriptor;

/**
 * Interpolate a given constraint violation message.
 * Implementations should be as tolerant as possible on syntax errors.
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
public interface MessageInterpolator {
	/**
	 * Interpolate the message template based on the contraint validation context.
	 * The locale is defaulted according to the <code>MessageInterpolator</code>
	 * implementation. See the implementation documentation for more detail.
	 *
	 * @param messageTemplate The message to interpolate.
	 * @param context contextual information related to the interpolation
	 *
	 * @return Interpolated error message.
	 */
	String interpolate(String messageTemplate, Context context);

	/**
	 * Interpolate the message template based on the contraint validation context.
	 * The <code>Locale</code> used is provided as a parameter.
	 *
	 * @param messageTemplate The message to interpolate.
	 * @param context contextual information related to the interpolation
	 * @param locale the locale targeted for the message
	 *
	 * @return Interpolated error message.
	 */
	//String interpolate(String messageTemplate, Context context,  Locale locale);

	/**
	 * Information related to the interpolation context
	 */
	interface Context {
		/**
		 * @return ConstraintDescriptor corresponding to the constraint being validated
		 */
		ConstraintDescriptor<?> getConstraintDescriptor();

		/**
		 * @return value being validated
		 */
		Object getValidatedValue();
	}
}
