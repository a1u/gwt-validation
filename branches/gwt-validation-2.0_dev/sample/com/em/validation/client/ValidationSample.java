package com.em.validation.client;

import com.em.validation.client.reflector.IReflectorFactory;
import com.em.validation.client.reflector.ReflectorFactory;
import com.google.gwt.core.client.EntryPoint;

public class ValidationSample implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		//get reflector factory
		IReflectorFactory factory = ReflectorFactory.INSTANCE;
		
		if(factory == null) {
			com.google.gwt.user.client.Window.alert("no factory!");
		}
		
	}	

}
