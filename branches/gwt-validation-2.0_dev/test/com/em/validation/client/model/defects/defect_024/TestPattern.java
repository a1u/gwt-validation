package com.em.validation.client.model.defects.defect_024;

import javax.validation.constraints.Pattern;

public class TestPattern {

	@Pattern(regexp="\\S*")
	private String stringToTestRegex = "";

	public String getStringToTestRegex() {
		return stringToTestRegex;
	}

	public void setStringToTestRegex(String stringToTestRegex) {
		this.stringToTestRegex = stringToTestRegex;
	}

}
