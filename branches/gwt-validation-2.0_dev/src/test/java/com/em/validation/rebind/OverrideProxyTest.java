package com.em.validation.rebind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Payload;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.junit.Test;

import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.MaxGroup;
import com.em.validation.rebind.reflector.AnnotationProxyFactory;

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
	}
	
}
