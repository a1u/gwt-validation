package com.em.validation.client.model.defects.defect_068;

public class Defect68_TestData {

	@AllowedValues({Values.FOO, Values.BAR})
	private Values value;

	public Values getValue() {
		return value;
	}

	public void setValue(final Values value) {
		this.value = value;
	}
}