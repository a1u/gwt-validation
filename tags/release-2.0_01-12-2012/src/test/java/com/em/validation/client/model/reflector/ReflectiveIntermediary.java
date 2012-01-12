package com.em.validation.client.model.reflector;

import javax.validation.constraints.NotNull;

public class ReflectiveIntermediary extends ReflectiveSkip implements ReflectiveSide01AtIntermediaryLayer, ReflectiveSide02AtIntermediaryLayer{

	@NotNull
	private String reflectiveIntermediaryLayerString = null;

	public String getReflectiveIntermediaryLayerString() {
		return reflectiveIntermediaryLayerString;
	}

	public void setReflectiveIntermediaryLayerString(String reflectiveIntermediaryLayerString) {
		this.reflectiveIntermediaryLayerString = reflectiveIntermediaryLayerString;
	}

	@Override
	public String getReflectiveSide01AtIntermediaryLayerString() {
		return "reflectiveSide01AtIntermediaryLayerString";
	}	
	
	@Override
	public String getReflectiveSide02AtIntermediaryLayerString() {
		return "reflectiveSide02AtIntermediaryLayerString";
	}	
}
