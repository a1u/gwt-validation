package com.em.validation.client.model.sequence;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import com.em.validation.client.constraints.NotEmpty;
import com.em.validation.client.model.groups.BasicGroup;
import com.em.validation.client.model.groups.ExtendedGroup;
import com.em.validation.client.model.groups.MaxGroup;

@GroupSequence({BasicGroup.class, ExtendedGroup.class, MaxGroup.class})
public class ClassWithSequence {

	@NotEmpty(groups=BasicGroup.class)
	private String notEmpty = "12;";
	
	@AssertTrue(groups=ExtendedGroup.class)
	private boolean shouldBeTrue = true;
	
	@AssertFalse(groups=MaxGroup.class)
	private boolean shouldBeFalse = false;

	public String getNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(String notEmpty) {
		this.notEmpty = notEmpty;
	}

	public boolean isShouldBeTrue() {
		return shouldBeTrue;
	}

	public void setShouldBeTrue(boolean shouldBeTrue) {
		this.shouldBeTrue = shouldBeTrue;
	}

	public boolean isShouldBeFalse() {
		return shouldBeFalse;
	}

	public void setShouldBeFalse(boolean shouldBeFalse) {
		this.shouldBeFalse = shouldBeFalse;
	}	
	
}
