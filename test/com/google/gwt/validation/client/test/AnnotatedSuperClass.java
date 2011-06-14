package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.interfaces.IValidatable;

@TestClassLevelAnnotation
public class AnnotatedSuperClass implements IValidatable {

	private String name = "";
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
