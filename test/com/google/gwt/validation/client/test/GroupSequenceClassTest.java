package com.google.gwt.validation.client.test;

import com.google.gwt.validation.client.GroupSequence;
import com.google.gwt.validation.client.GroupSequences;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

@GroupSequences({
	@GroupSequence(name="default", sequence={"one.first","two.first","three.first"}),
	@GroupSequence(name="one", sequence={"one.first","one.second"}),
	@GroupSequence(name="two", sequence={"two.first","two.second"})
})
@GroupSequence(name="three", sequence={"three.first","three.second"})
public class GroupSequenceClassTest implements IValidatable {

	@NotNull(groups={"one.first", "one.second", "two.first", "three.first"})
	private String nullString = null;

	public String getNullString() {
		return nullString;
	}

	public void setNullString(String nullString) {
		this.nullString = nullString;
	}
	
	@NotNull(groups={"two.first"})
	private String anotherNullString = null;

	public String getAnotherNullString() {
		return anotherNullString;
	}

	public void setAnotherNullString(String anotherNullString) {
		this.anotherNullString = anotherNullString;
	}
}
