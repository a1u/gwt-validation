package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class CyclicTestChild implements IValidatable {

	@NotNull
	private CyclicTestParent parent = null;

	public CyclicTestParent getParent() {
		return parent;
	}

	public void setParent(CyclicTestParent parent) {
		this.parent = parent;
	}
	
	@NotNull
	private String nullstring = null;

	public String getNullstring() {
		return nullstring;
	}

	public void setNullstring(String nullstring) {
		this.nullstring = nullstring;
	}
	
}
