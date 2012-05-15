package com.em.validation.client.model.reflector;

public class OnlyInterfaces implements JustInterface01, JustInterface02 {

	@Override
	public String getInterface01String() {
		return "interface01String";
	}
	
	@Override
	public String getInterface02String() {
		return "interface02String";
	}

	@Override
	public String getJustBaseInterface() {
		return "justBaseInterface";
	}

}
