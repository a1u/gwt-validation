package com.em.validation.rebind.generator;

public enum GeneratorState {

	INSTANCE;
	
	private boolean usingGwtFeatures = false;
	
	private GeneratorState() {
		
	}
	
	public void setUsingGwtFeatures(boolean usingFeatures) {
		this.usingGwtFeatures = usingFeatures;
	}
	
	public boolean isUsingGwtFeatures() {
		return this.usingGwtFeatures;
	}
	
}
