package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class CyclicTestParent implements IValidatable {

	public CyclicTestParent() {
		this.ctc.setParent(this);
	}
	
	@Valid
	@NotNull(groups={"one"})
	@NotEmpty(groups={"two","three"})
	private CyclicTestChild ctc = new CyclicTestChild();
	
	@NotNull
	private String nullString = null;

	public CyclicTestChild getCtc() {
		return ctc;
	}

	public void setCtc(CyclicTestChild ctc) {
		this.ctc = ctc;
	}

	public String getNullString() {
		return nullString;
	}

	public void setNullString(String nullString) {
		this.nullString = nullString;
	}
	
	
	
}
