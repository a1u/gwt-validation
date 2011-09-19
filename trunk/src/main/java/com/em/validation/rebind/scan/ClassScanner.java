package com.em.validation.rebind.scan;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
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

import com.em.validation.rebind.config.RebindConfiguration;

/**
 * Uses Reflections library to perform various classpath scanning duties.
 * 
 * @author chris
 *
 */
public enum ClassScanner {

	INSTANCE;
	
	private Reflections reflections = null;

	/**
	 * List of jar files that we do not want to include on the classpath because they cannot/will not contain any bits of the 
	 * path that we want scanned.  this speeds up about x14 on a modern i7 laptop, 8gb of ram.
	 * 
	 */
	private String[] doNotScanJarsInThisList = new String[]{
		"gwt-dev",
		"gwt-user",
		"guava",
		"surefire",
		"junit",
		"javassist",
		"servlet",
		"slf4j",
		"logback",
		"gson",
		"freemarker",
		"google-collections",
		"dom4j",
		"localedata",
		"xml-apis",
		"reflections"
	};
	
	private ClassScanner() {
		
		//this little snippet goes through the classpath urls and ommits jars that are on the forbidden list.
		//this is intended to remove jars from the classpath that we know are not ones that will contain patterns
		Set<URL> classPathUrls = ClasspathHelper.forJavaClassPath();
		Set<URL> useableUrls = new HashSet<URL>();
		for(URL url : classPathUrls) {
			boolean use = true;
			for(String jar : this.doNotScanJarsInThisList) {
				if(url.toString().contains(jar)) {
					use = false;
					break;
				}
			}
			if(use) {
				useableUrls.add(url);
			}
			use = false;
		}		
		
		ConfigurationBuilder builder = new ConfigurationBuilder()
										.setUrls(useableUrls)
										.setScanners(	new TypeAnnotationsScanner(), 
														new FieldAnnotationsScanner(), 
														new MethodAnnotationsScanner(), 
														new SubTypesScanner()
										)
										.useParallelExecutor()
										;
			
		this.reflections = new Reflections(builder);
	}
	
	public Set<Class<?>> getConstrainedClasses(String excludedPattern) {
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
						//taken as part of a fix for issue 34, by Niels, this will NOT ALLOW matched model classes to have code generated for them
						if(excludedPattern != null && annotatedWith.getName().matches(excludedPattern)) continue;
						result.add(annotatedWith);
					}
				}
				
				for(Field annotatedWith : this.reflections.getFieldsAnnotatedWith(annotation)) {
					if(!annotatedWith.getDeclaringClass().isAnnotation()) {
						//taken as part of a fix for issue 34, by Niels, this will NOT ALLOW matched model classes to have code generated for them
						if(excludedPattern != null && annotatedWith.getDeclaringClass().getName().matches(excludedPattern)) continue;
						result.add(annotatedWith.getDeclaringClass());
					}
				}
				
				for(Method annotatedWith : this.reflections.getMethodsAnnotatedWith(annotation)) {
					//taken as part of a fix for issue 34, by Niels, this will NOT ALLOW matched model classes to have code generated for them
					if(!annotatedWith.getDeclaringClass().isAnnotation()) {
						if(excludedPattern != null && annotatedWith.getDeclaringClass().getName().matches(excludedPattern)) continue;
						result.add(annotatedWith.getDeclaringClass());
					}
				}
			}
		}
		
		return result;
	}
	
	public Set<Class<?>> getConstrainedClasses() {
		return this.getConstrainedClasses(RebindConfiguration.INSTANCE.excludedModelClassesRegularExpression());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<Class<? extends ConstraintValidator<?, ?>>> getConstraintValidatorClasses(String excludedPattern) {
		//create empty result set
		Set<Class<? extends ConstraintValidator<?, ?>>> result = new LinkedHashSet<Class<? extends ConstraintValidator<?,?>>>();
		
		Set<Class<? extends ConstraintValidator>> subTypes = new HashSet<Class<? extends ConstraintValidator>>();
		subTypes.addAll(this.reflections.getSubTypesOf(ConstraintValidator.class));
		
		for(Class<? extends ConstraintValidator> validatorClass : subTypes) {
			//submitted as part of a fix for issue 34, by Niels, this will NOT ALLOW matched classes to be used as validators
			if(excludedPattern != null && validatorClass.getName().matches(excludedPattern)) continue;			
			result.add((Class<? extends ConstraintValidator<?, ?>>) validatorClass);			
		}
		
		return result;
	}
	
	public Set<Class<? extends ConstraintValidator<?, ?>>> getConstraintValidatorClasses() {
		return this.getConstraintValidatorClasses(RebindConfiguration.INSTANCE.excludedValidatorClassesRegularExpression());
	}
	
	public Set<Class<?>> getUncoveredImplementors() {
		Set<Class<?>> uncovered = new LinkedHashSet<Class<?>>();
		Set<Class<?>> constrainedClasses = this.getConstrainedClasses();
		
		for(Class<?> constrained : constrainedClasses) {
			uncovered.addAll(this.reflections.getSubTypesOf(constrained));			
		}
		
		uncovered.removeAll(constrainedClasses);

		return uncovered;
	}
}
