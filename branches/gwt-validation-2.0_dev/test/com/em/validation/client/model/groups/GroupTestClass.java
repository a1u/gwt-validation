package com.em.validation.client.model.groups;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class GroupTestClass extends ParentGroupClass {

	@Min(12)
	private double testDouble = 0;
	
	@Min(value=-12, groups={Default.class,ExtendedGroup.class,MaxGroup.class})
	private int testInt = 0;
	
	@NotNull(groups={BasicGroup.class,ExtendedGroup.class,MaxGroup.class,Default.class})
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
