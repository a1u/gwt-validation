package com.google.gwt.validation.client.test;

import java.util.Map;

import com.google.gwt.validation.client.interfaces.IConstraint;

public class TestAnnotationConstraint implements IConstraint<TestClassLevelAnnotation> {

	public void initialize(TestClassLevelAnnotation constraintAnnotation) {

		
	}

	public void initialize(Map<String, String> propertyMap) {

		
	}

	public boolean isValid(Object value) {

		//cannot be null
		if(value == null) return false;
		
		boolean valid = false;
		
		if(!valid) {
			
			try {
			
				AnnotatedSuperClass ac = (AnnotatedSuperClass)value;
											//under java6 this would use the
											//idiom !.isEmpty() so preserving
											//the same usage of !
				if(ac.getName() != null && !(ac.getName().trim().length() == 0)) {
					valid = true;
				}
				
			} catch (ClassCastException ccex) {
				//do nothing, invalid still
				//ccex.printStackTrace();
			} catch (Exception ex) {
				//do nothing, invalid still
				//ex.printStackTrace();
			}
			
		}
		
		return valid;
	}
	
}
