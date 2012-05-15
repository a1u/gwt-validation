package com.em.validation.client.model.defects.defect_041;

import java.util.Set;

public class Defect41DomainObject {

	@Defect41ConstraintAnnotation(myEnum=Defect41Enum.FIRST_VALUE)
	private Set<Defect41Enum> enumValues;

	public Set<Defect41Enum> getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(Set<Defect41Enum> enumValues) {
		this.enumValues = enumValues;
	}
	
}
