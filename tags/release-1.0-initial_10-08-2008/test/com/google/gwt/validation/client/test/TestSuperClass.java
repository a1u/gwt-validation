package com.google.gwt.validation.client.test;

import java.util.Date;

import com.google.gwt.validation.client.Past;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.interfaces.IValidatable;

public abstract class TestSuperClass implements TestSuperInterface, IValidatable {

	@Past(groups={"default", "four"})
	//group four has no error here (initially)
	private Date past = new Date(0);

	public void setPast(Date past) {
		this.past = past;
	}

	public Date getPast() {
		return past;
	}
	
	// method!
	@Pattern(pattern=".*")
	public String patternMethod() {
		return "text";
	}
	
	//annotation on abstract
	@Pattern(pattern=".*", groups={"default", "five"})
	public abstract String abstractPatternMethod();
	
	//implemented method from interface
	//1 fail in group five
	public Date failFutureDate() {
		return new Date(0);
	}
	
	//1 failure by default
}
