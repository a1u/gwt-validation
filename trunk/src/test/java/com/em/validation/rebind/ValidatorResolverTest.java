package com.em.validation.rebind;

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

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.junit.Assert;
import org.junit.Test;

import com.em.validation.client.model.generic.TestClass;
import com.em.validation.client.validators.NotNullObjectValidator;
import com.em.validation.rebind.resolve.ValidatorResolver;

public class ValidatorResolverTest {

	@Test
	public void testValidatorResolver() {
		
		Set<Class<?>> validatedBy = ValidatorResolver.INSTANCE.getValidatorClassesForAnnotation(NotNull.class, Object.class);
		Assert.assertEquals(1, validatedBy.size());
		Assert.assertEquals(NotNullObjectValidator.class, validatedBy.iterator().next());
		

		validatedBy = ValidatorResolver.INSTANCE.getValidatorClassesForAnnotation(NotNull.class, TestClass.class);
		Assert.assertEquals(1, validatedBy.size());
		Assert.assertEquals(NotNullObjectValidator.class, validatedBy.iterator().next());

	}
	
}
