package com.em.validation.client.model.validator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChildNode {

	@NotNull(message="This should always be null, no big deal")
	private String alwaysNull;
	
	@Size(min=3, message="This string is always shorter than {min}")
	private String alwaysTooShort;

	public ChildNode() {
		this.setAlwaysNull(null);
		this.setAlwaysTooShort("12");
	}
	
	public String getAlwaysNull() {
		return alwaysNull;
	}

	public void setAlwaysNull(String alwaysNull) {
		this.alwaysNull = alwaysNull;
	}

	public String getAlwaysTooShort() {
		return alwaysTooShort;
	}

	public void setAlwaysTooShort(String alwaysTooShort) {
		this.alwaysTooShort = alwaysTooShort;
	}
	
}
