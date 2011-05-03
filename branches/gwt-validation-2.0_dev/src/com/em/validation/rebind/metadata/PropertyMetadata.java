package com.em.validation.rebind.metadata;

import java.util.ArrayList;
import java.util.List;

public class PropertyMetadata {


	private List<String> annotations = new ArrayList<String>();
	
	private String name;
	private String accessor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccessor() {
		return accessor;
	}
	public void setAccessor(String accessor) {
		this.accessor = accessor;
	}

	public List<String> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
		
}
