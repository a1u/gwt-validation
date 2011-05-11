package com.em.validation.client.model.generic;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class ParentClass implements ParentInterface {

	@NotNull
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
