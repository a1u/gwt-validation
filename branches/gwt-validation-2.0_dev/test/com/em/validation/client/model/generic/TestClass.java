package com.em.validation.client.model.generic;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TestClass extends ParentClass implements TestInterface {

	@NotNull
	@Pattern.List( {
		@Pattern(regexp=".+"),
		@Pattern(regexp="--")
	})
	@Size(min=2, max=12)
	public String publicTestString = "";
	
	@Min(5)
	public int publicTestInt = 0;
	
	private String testString = "testtesttest";
	
	private int testInt = 0;

	@NotNull
	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	@Max(10)
	@Min(5)
	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}

	@Size(min=1)
	@Override
	public String getTestInterfaceString() {
		return null;
	}

	@Size(min=1)
	@Override
	public String getParentAbstractString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
