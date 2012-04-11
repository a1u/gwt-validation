package com.em.validation.client.model.defects.defect_070;

import javax.validation.constraints.Pattern;

import com.em.validation.client.constraints.NotEmpty;

/**
 * Pattern test class
 * 
 * @author chris
 *
 */
public class PatternTestClass {

	@NotEmpty //includes @NotNull
	@Pattern.List({
		@Pattern(regexp = "^([0-9]|\\+| ])*$", message="number pattern"),
		@Pattern(regexp = "^[a-z]{0,3}$", message="letter pattern")
	})
	private String testString = null;

	@Pattern(regexp = "^[a-z]{0,2}$", message="small letter pattern")
	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}
}
