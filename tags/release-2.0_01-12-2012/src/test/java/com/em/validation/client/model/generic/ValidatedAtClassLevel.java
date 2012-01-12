package com.em.validation.client.model.generic;

import com.em.validation.client.model.constraint.ClassLevelConstraint;

@ClassLevelConstraint
public class ValidatedAtClassLevel {

	private boolean valid = false;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
