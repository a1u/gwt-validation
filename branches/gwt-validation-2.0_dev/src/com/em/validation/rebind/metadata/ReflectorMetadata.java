package com.em.validation.rebind.metadata;

import java.util.HashSet;
import java.util.Set;

public class ReflectorMetadata {

	private String reflectorClass = "";
	private String targetClass = "";
	private String superClass = "";
	private Set<String> reflectorInterfaces = new HashSet<String>();
	
	public String getReflectorClass() {
		return reflectorClass;
	}
	public void setReflectorClass(String reflectorClass) {
		this.reflectorClass = reflectorClass;
	}
	
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	
	public String getSuperClass() {
		return superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	
	public Set<String> getReflectorInterfaces() {
		return reflectorInterfaces;
	}
	public void setReflectorInterfaces(Set<String> reflectorInterfaces) {
		this.reflectorInterfaces = reflectorInterfaces;
	}
	
}
