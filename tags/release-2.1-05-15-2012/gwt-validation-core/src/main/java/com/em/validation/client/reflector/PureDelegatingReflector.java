package com.em.validation.client.reflector;


public class PureDelegatingReflector extends AbstractCompiledReflector {
	
	@Override
	public Object getValue(String name, Object target) {
		//wired up to deliver super values straight out
		return this.getSuperValues(name, target);
	}

}
