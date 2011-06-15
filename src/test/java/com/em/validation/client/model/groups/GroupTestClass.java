package com.em.validation.client.model.groups;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

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
import javax.validation.groups.Default;

public class GroupTestClass extends ParentGroupClass {

	@Min(value=12)
	private double testDouble = 0;
	
	@Min(value=-12, groups={Default.class,ExtendedGroup.class,MaxGroup.class})
	private int testInt = 0;
	
	@NotNull(groups={BasicGroup.class,ExtendedGroup.class,MaxGroup.class,DefaultExtGroup.class})
	@Size(min=5, max=12, groups={MaxGroup.class, Default.class})
	private String testString = "";

	@Max(value=400, groups={MaxGroup.class})
	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}

	@Pattern(regexp=".+",groups={ExtendedGroup.class})
	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
	
	public double getTestDouble() {
		return testDouble;
	}

	public void setTestDouble(double testDouble) {
		this.testDouble = testDouble;
	}
}
