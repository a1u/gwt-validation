package com.em.validation.rebind.scan;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Uses Reflections library to perform various classpath scanning duties.
 * 
 * @author chris
 *
 */
public enum ClassScanner {

	INSTANCE;
	
	private Reflections reflections = null;
	
	private ClassScanner() {
		ConfigurationBuilder builder = new ConfigurationBuilder()
										.setUrls(ClasspathHelper.getUrlsForCurrentClasspath())
										.setScanners(new TypeAnnotationsScanner(), new FieldAnnotationsScanner(), new MethodAnnotationsScanner(), new SubTypesScanner());
		
		this.reflections = new Reflections(builder);
	}
	
	public Set<Class<?>> getConstrainedClasses(String pattern) {
		//create empty result set
		Set<Class<?>> result = new LinkedHashSet<Class<?>>();
		
		//get everything annotated with @javax.validation.Constraint
		Set<Class<?>> constraints = this.reflections.getTypesAnnotatedWith(Constraint.class);
		
		//for each Constraint found in the above line, look for classes annotated with that constraint
		for(Class<?> constraint : constraints) {
			if(constraint != null && constraint.isAnnotation()) {
				@SuppressWarnings("unchecked")
				Class<? extends Annotation> annotation = (Class<? extends Annotation>)constraint; 
				
				for(Class<?> annotatedWith : this.reflections.getTypesAnnotatedWith(annotation)) {
					if(!annotatedWith.isAnnotation()) {
						result.add(annotatedWith);
					}
				}
				
				for(Field annotatedWith : this.reflections.getFieldsAnnotatedWith(annotation)) {
					if(!annotatedWith.getDeclaringClass().isAnnotation()) {
						result.add(annotatedWith.getDeclaringClass());
					}
				}
				
				for(Method annotatedWith : this.reflections.getMethodsAnnotatedWith(annotation)) {
					if(!annotatedWith.getDeclaringClass().isAnnotation()) {
						result.add(annotatedWith.getDeclaringClass());
					}
				}
			}
		}
		
		return result;
	}
	
	public Set<Class<?>> getConstrainedClasses() {
		return this.getConstrainedClasses(".*");
	}
	
	@SuppressWarnings("unchecked")
	public Set<Class<? extends ConstraintValidator<?, ?>>> getConstraintValidatorClasses(String pattern) {
		//create empty result set
		Set<Class<? extends ConstraintValidator<?, ?>>> result = new LinkedHashSet<Class<? extends ConstraintValidator<?,?>>>();
		
		for(@SuppressWarnings("rawtypes") Class<? extends ConstraintValidator> validatorClass : this.reflections.getSubTypesOf(ConstraintValidator.class)) {
			result.add((Class<? extends ConstraintValidator<?, ?>>) validatorClass);			
		}
		
		return result;
	}
	
	public Set<Class<? extends ConstraintValidator<?, ?>>> getConstraintValidatorClasses() {
		return this.getConstraintValidatorClasses(".*");
	}
	
}
