package com.em.validation.client.model.defects.defect_045;

import javax.validation.constraints.NotNull;

public class Defect45_Example {

	public interface Defect45_Group {}
	
	@NotNull(groups={Defect45_Group.class})
	private String field = null;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}	
}
