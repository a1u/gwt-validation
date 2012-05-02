package com.em.validation.rebind.scan;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.ValidationException;

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
	
	//lock on scanning/reading (1 at a time, fair)
	private Semaphore scanningLockSempahore = new Semaphore(1,true);

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
		"reflections",
		"sunjce",
		"dnsns"
	};
	
	private ClassScanner() {
		scan();
	}
	
	private void scan() {
		//acquire scan lock
		try {
			this.scanningLockSempahore.acquire();
		} catch (InterruptedException e) {
			throw new ValidationException("An interpution occured while scanning for resources.");
		}
		

		//urls to scan from
		Set<URL> classPathUrls = new HashSet<URL>();

		//model packages from configuration
		Set<String> modelPackages = RebindConfiguration.INSTANCE.includedModelPackages();
		
		if(modelPackages != null && !modelPackages.isEmpty()) {
			//add all model packages
			for(String modelPackage : modelPackages) {
				classPathUrls.addAll(ClasspathHelper.forPackage(modelPackage));
			}
		} else { //otherwise, grab all classpaths and use them, as a best guess
			
			//start with base classpaths
			classPathUrls.addAll(ClasspathHelper.forClassLoader(Thread.currentThread().getContextClassLoader()));
			classPathUrls.addAll(ClasspathHelper.forJavaClassPath());
			
			//this little snippet goes through the classpath urls and ommits jars that are on the forbidden list.
			//this is intended to remove jars from the classpath that we know are not ones that will contain (valid/usable) patterns
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
			
			//use the usable urls as the overall classpath urls
			classPathUrls = useableUrls;
		}
		
		//create a reflections configuration object
		ConfigurationBuilder builder = new ConfigurationBuilder()
										.setUrls(classPathUrls)
										.setScanners(	new TypeAnnotationsScanner(), 
														new FieldAnnotationsScanner(), 
														new MethodAnnotationsScanner(), 
														new SubTypesScanner()
										)
										//see gwt-validation issue #64 (http://code.google.com/p/gwt-validation/issues/detail?id=64)
										//see reflections issue #92 (http://code.google.com/p/reflections/issues/detail?id=92)
										//do not use parallel executor until this is fixed
										//.useParallelExecutor()
										;
			
		this.reflections = new Reflections(builder);
		
		//release scan lock
		this.scanningLockSempahore.release();
	}
	
	public Set<Class<?>> getConstrainedClasses(String excludedPattern) {
		//acquire scan lock
		try {
			this.scanningLockSempahore.acquire();
		} catch (InterruptedException e) {
			throw new ValidationException("An interpution occured while getting constrained classes.");
		}
		
		//create empty result set
		Set<Class<?>> result = new LinkedHashSet<Class<?>>();
		
		//empty constraint set
		Set<Class<?>> constraints = new HashSet<Class<?>>();
		//get everything annotated with @javax.validation.Constraint
		constraints.addAll(this.reflections.getTypesAnnotatedWith(Constraint.class));
		//also account for classes, fields, and method with only @Valid
		constraints.add(Valid.class);
		
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
		
		//release scan lock
		this.scanningLockSempahore.release();
	
		return result;
	}
	
	public Set<Class<?>> getConstrainedClasses() {
		return this.getConstrainedClasses(RebindConfiguration.INSTANCE.excludedModelClassesRegularExpression());
	}
	
	/**
	 * This method is designed to return all of the classes that <i>implement</i> a constrained class but <b>do not</b> have 
	 * annotations of their own.  This allows the generation step to create a way (through string matching) to grab the 
	 * interfaces of those classes.  extended classes don't have this problem because gwt's JRE emulation implements 
	 * the getSuperclass() method but not getInterfaces(). 
	 * 
	 * @return
	 */
	public Set<Class<?>> getUncoveredImplementors() {
		
		Set<Class<?>> uncovered = new LinkedHashSet<Class<?>>();
		Set<Class<?>> constrainedClasses = this.getConstrainedClasses();
		
		//acquire scan lock
		try {
			this.scanningLockSempahore.acquire();
		} catch (InterruptedException e) {
			throw new ValidationException("An interpution occured while getting uncovered implementors.");
		}
		
		for(Class<?> constrained : constrainedClasses) {
			uncovered.addAll(this.reflections.getSubTypesOf(constrained));			
		}
		
		uncovered.removeAll(constrainedClasses);
		
		//release scan lock
		this.scanningLockSempahore.release();

		return uncovered;
	}
}
