package com.em.validation.client.model.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class ClassWithIterable {

	@Size(min=1, message="The test list needs a size greater than {min}")
	@Valid
	private List<ChildNode> testIterable = new ArrayList<ChildNode>();

	public List<ChildNode> getTestIterable() {
		return testIterable;
	}

	public void setTestIterable(List<ChildNode> testIterable) {
		this.testIterable = testIterable;
	}
	
}
