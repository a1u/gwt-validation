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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import com.em.validation.client.regex.RegexProvider;

public enum ConstrainedClassScanner {

	INSTANCE;
	
	private ConstrainedClassScanner() {
		
	}
	
	public Set<Class<?>> getConstrainedClasses(String pattern) {
		Set<Class<?>> result = new LinkedHashSet<Class<?>>();
		
		URL[] urls = ClasspathUrlFinder.findClassPaths();
		
		AnnotationDB db = new AnnotationDB();
		try {
			//set and scan
			db.setScanClassAnnotations(true);
			db.setScanFieldAnnotations(true);
			db.setScanMethodAnnotations(true);
			db.setScanParameterAnnotations(true);
			db.scanArchives(urls);
			
			//walk annotation db
			Map<String, Set<String>> index = db.getAnnotationIndex();
			for(String annotationName : index.keySet()) {
				Class<?> annotationClass = null;
				try {
					//get the class of the annotation class
					 annotationClass = Class.forName(annotationName);
				} catch (ClassNotFoundException e) {
					//nothing to do, couldn't get class instance
				}
				if(annotationClass != null) {
					if(annotationClass.getAnnotation(javax.validation.Constraint.class) != null) {
						for(String constrainedClassName: index.get(annotationClass.getName())) {
							if(RegexProvider.INSTANCE.matches(pattern, constrainedClassName)) {
								try {
									Class<?> constrainedClass = Class.forName(constrainedClassName);
									result.add(constrainedClass);
									if(!Object.class.equals(constrainedClass.getSuperclass()) && constrainedClass.getSuperclass() != null) {
										result.add(constrainedClass.getSuperclass());
									}
									if(constrainedClass.getInterfaces().length > 0) {
										result.addAll(Arrays.asList(constrainedClass.getInterfaces()));
									}
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	public Set<Class<?>> getConstrainedClasses() {
		return this.getConstrainedClasses(".*");
	}
	
}
