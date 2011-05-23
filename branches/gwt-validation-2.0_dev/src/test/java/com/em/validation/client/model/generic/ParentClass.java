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
import javax.validation.constraints.Size;

public abstract class ParentClass implements ParentInterface {

	@NotNull(message="NOT NULL PARENT")
	@Size(min=4)
	public String publicParentString = "publicParentString";
	
	@Max(22)
	private int parentInt = 0;

	@Min(0)
	public int getParentInt() {
		return parentInt;
	}

	public void setParentInt(int counter) {
		this.parentInt = counter;
	}

	@Override
	@Max(400)
	public int getParentInterfaceInt() {
		return 0;
	}	
	
	@NotNull
	public abstract String getParentAbstractString();
}
