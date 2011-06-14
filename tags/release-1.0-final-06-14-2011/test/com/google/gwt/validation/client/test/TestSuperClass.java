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

import java.util.Date;

import com.google.gwt.validation.client.Past;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.interfaces.IValidatable;

public abstract class TestSuperClass implements TestSuperInterface, IValidatable {

	@Past(groups={"default", "four"})
	//group four has no error here (initially)
	private Date past = new Date(0);

	public void setPast(Date past) {
		this.past = past;
	}

	public Date getPast() {
		return past;
	}
	
	// method!
	@Pattern(pattern=".*")
	public String patternMethod() {
		return "text";
	}
	
	//annotation on abstract
	@Pattern(pattern=".*", groups={"default", "five"})
	public abstract String abstractPatternMethod();
	
	//implemented method from interface
	//1 fail in group five
	public Date failFutureDate() {
		return new Date(0);
	}
	
	//1 failure by default
}
