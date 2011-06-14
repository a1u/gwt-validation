package com.google.gwt.validation.client.test;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

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
