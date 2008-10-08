package com.google.gwt.validation.client.test;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.GroupSequences;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

@GroupSequences({
	@GroupSequence(name="default", sequence={"one.first","two.first","three.first"}),
	@GroupSequence(name="one", sequence={"one.first","one.second"}),
	@GroupSequence(name="two", sequence={"two.first","two.second"})
})
@GroupSequence(name="three", sequence={"three.first","three.second"})
public class GroupSequenceClassTest implements IValidatable {

	@NotNull(groups={"one.first", "one.second", "two.first", "three.first"})
	private String nullString = null;

	public String getNullString() {
		return nullString;
	}

	public void setNullString(String nullString) {
		this.nullString = nullString;
	}
	
	@NotNull(groups={"two.first"})
	private String anotherNullString = null;

	public String getAnotherNullString() {
		return anotherNullString;
	}

	public void setAnotherNullString(String anotherNullString) {
		this.anotherNullString = anotherNullString;
	}
}
