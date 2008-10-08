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

import java.util.Date;

import com.google.gwt.validation.client.AssertFalse;
import com.google.gwt.validation.client.AssertTrue;
import com.google.gwt.validation.client.Future;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.Patterns;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class TestModel extends TestSuperClass implements IValidatable, TestModelInterface {

	@NotNull(message="date cannot be null", groups={"default", "one","two","three"})
	//groups one, two, and three should produce 1 invalid constraint here
	private Date date = null;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@AssertFalse(message="should be false", groups={"default", "two"})
	//group two should produce 1 invalid constraint here
	private boolean shouldBeFalse = true;

	public boolean isShouldBeFalse() {
		return shouldBeFalse;
	}

	public void setShouldBeFalse(boolean shouldBeFalse) {
		this.shouldBeFalse = shouldBeFalse;
	}
	
	@AssertTrue(message="should be true", groups={"default", "one"})
	//group one should produce 1 invalid constraint here
	private boolean shouldBeTrue = false;

	public boolean isShouldBeTrue() {
		return shouldBeTrue;
	}

	public void setShouldBeTrue(boolean shouldBeTrue) {
		this.shouldBeTrue = shouldBeTrue;
	}
	
	@NotNull(message="should not be null")
	@NotEmpty(message="should not be empty", groups={"default", "three"})
	//group three should produce 1 invalid constraint here
	private String shouldNotBeEmpty = "";

	public String getShouldNotBeEmpty() {
		return shouldNotBeEmpty;
	}

	public void setShouldNotBeEmpty(String shouldNotBeEmpty) {
		this.shouldNotBeEmpty = shouldNotBeEmpty;
	}
	
	@Patterns({
		@Pattern(pattern=".*", message="pattern should match", groups={"default", "three"}),
		@Pattern(pattern=".*", message="pattern should match", groups={"default", "two"})
	})
	//group three should produce 0 invalid constraints here
	//group two should produce 0 invalid constraints here
	private String patternString = "";

	public String getPatternString() {
		return patternString;
	}

	public void setPatternString(String patternString) {
		this.patternString = patternString;
	}
	
	//=================== METHODS =========================
	
	@Future
	//0 invalid constraints here
	public Date future() {
		return new Date(new Date().getTime() + 10000000);
	}
	
	//=================== Methods that don't matter ===============
	
	@NotNull
	//should not do anything
	public void someVoidMethod() {
		
	}
	
	@NotNull
	//should not do anything
	public void anotherVoidMethod() {
		
	}
	
	@NotNull
	public String yetAnnotherMethod(String too, String many, String parameters) {
		return null;
	}
	
	//=================== Implemented methods that have annotation in Interface ========

	//one annotation from interface
	//group one, should fail here
	public Date notInFuture() {
		return new Date(new Date().getTime() + 10000000);
	}
	
	//should not fail here from abstract annotation
	public String abstractPatternMethod() {
		return "";
	}
	
	//six failures by default! (five from this class, one from method in superclass)
}
