package com.em.validation.client.model.sequence;

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
