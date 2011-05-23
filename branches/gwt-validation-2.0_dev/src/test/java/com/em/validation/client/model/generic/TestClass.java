package com.em.validation.client.model.generic;

/*
(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.em.validation.client.model.constraint.FakeConstraint;

public class TestClass extends ParentClass implements TestInterface {

	@NotNull(message="NOT NULL 1")
	@Pattern.List( {
		@Pattern(regexp=".+"),
		@Pattern(regexp="--")
	})
	@FakeConstraint
	@Size(min=2, max=12)
	public String publicTestString = "publicTestString";
	
	@Min(4)
	public int publicTestInt = 0;
	
	private String testString = "testString";
	
	private int testInt = 0;

	@NotNull(message="NOT NULL 2")
	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	@Max(10)
	@Min(5)
	@FakeConstraint
	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}

	@Size(min=1)
	@Override
	public String getTestInterfaceString() {
		return "testInterfaceString";
	}

	@Size(min=2)
	@Override
	public String getParentAbstractString() {
		return "parentAbstractString";
	}
	
}
