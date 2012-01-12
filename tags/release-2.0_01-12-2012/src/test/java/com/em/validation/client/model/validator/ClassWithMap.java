package com.em.validation.client.model.validator;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class ClassWithMap {

	@Size(min=1, message="The test map needs a size greater than {min}")
	@Valid
	private Map<String, ChildNode> testMap = new HashMap<String, ChildNode>();

	public Map<String, ChildNode> getTestMap() {
		return testMap;
	}

	public void setTestMap(Map<String, ChildNode> testMap) {
		this.testMap = testMap;
	}
	
}
