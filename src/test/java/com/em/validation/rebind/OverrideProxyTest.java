package com.em.validation.rebind;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.Payload;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import org.junit.Test;

import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.client.model.override.OverrideTestClass;
import com.em.validation.rebind.reflector.AnnotationProxyFactory;
import com.em.validation.rebind.resolve.ConstraintDescriptionResolver;

public class OverrideProxyTest {
	
	@Test
	public void testOverrideProxy() throws InstantiationException, IllegalAccessException {
		
		Size size = new Size() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return Size.class;
			}

			@Override
			public Class<?>[] groups() {
				return new Class<?>[]{Default.class};
			}

			@Override
			public int max() {
				return 22;
			}

			@Override
			public String message() {
				return null;
			}

			@Override
			public int min() {
				return 0;
			}

			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
		};
		
		//create value map
		Map<String,Object> overMap = new HashMap<String, Object>();
		overMap.put("max", 14);
		overMap.put("groups", new Class<?>[]{MaxGroup.class,ExtendedGroup.class});
		
		//get override instance
		Size overriden = AnnotationProxyFactory.INSTANCE.getProxy(size, overMap);
				
		//get equals
		assertEquals(size.min(), overriden.min());
		assertTrue(size.max() != overriden.max());
		
		//groups
		assertTrue(size.groups()[0] != overriden.groups()[0]);
		
		//signatures
		assertTrue(!size.toString().equals(overriden.toString()));
	}
	
	@Test
	public void testOverrideOnAnnotationFromClass() {
		
		OverrideTestClass test = new OverrideTestClass();
		
		//constraint descriptors
		Set<ConstraintDescriptor<? extends Annotation>> descriptors = ConstraintDescriptionResolver.INSTANCE.getConstraintsForProperty(test.getClass(), "testString");
		
		//constraint descriptor list should be predictable in size
		assertNotNull(descriptors);
		assertEquals(1, descriptors.size());

		@SuppressWarnings("unchecked")
		ConstraintDescriptor<Size> testDescriptor = (ConstraintDescriptor<Size>)descriptors.iterator().next();
		
		//get annotation
		Size size = testDescriptor.getAnnotation();
		
		//create overrides
		Map<String,Object> overMap = new HashMap<String, Object>();
		overMap.put("max", 22);
		overMap.put("groups", new Class<?>[]{MaxGroup.class,ExtendedGroup.class});
		
		//get proxy instance
		Size over = AnnotationProxyFactory.INSTANCE.getProxy(size, overMap);	
			
		//check groups (default values and non-default)
		assertArrayEquals(new Class<?>[]{}, size.groups());
		assertArrayEquals(new Class<?>[]{MaxGroup.class,ExtendedGroup.class}, over.groups());
		
		//check override values
		assertEquals(240,size.max());
		assertEquals(22,over.max());
				
		//check that non-override values are exactly the same
		assertEquals(size.min(),over.min());
		
		//check that the message is exactly the same
		assertEquals(size.message(), over.message());
	}
}
