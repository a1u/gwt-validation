package com.google.gwt.validation.client.test.jsr303;

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

import com.google.gwt.validation.client.Length;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class Country implements IValidatable {
	@NotNull
	private String name;
	@Length(maximum=2) 
	private String ISO2Code;
	@Length(maximum=3) 
	private String ISO3Code;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getISO2Code() {
		return ISO2Code;
	}
	public void setISO2Code(String ISO2Code) {
		this.ISO2Code = ISO2Code;
	}
	public String getISO3Code() {
		return ISO3Code;
	}
	public void setISO3Code(String ISO3Code) {
		this.ISO3Code = ISO3Code;
	}
}