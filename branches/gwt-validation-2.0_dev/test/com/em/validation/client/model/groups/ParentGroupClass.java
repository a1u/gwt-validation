package com.em.validation.client.model.groups;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

public class ParentGroupClass {

	@NotNull(groups={Default.class,BasicGroup.class})
	@Size(max=12,min=4,groups={MaxGroup.class})
	private String parentSizeConstrainedString = "parentSizeConstrainedString";

	public String getParentSizeConstrainedString() {
		return parentSizeConstrainedString;
	}

	public void setParentSizeConstrainedString(String parentSizeConstrainedString) {
		this.parentSizeConstrainedString = parentSizeConstrainedString;
	}
	
}
