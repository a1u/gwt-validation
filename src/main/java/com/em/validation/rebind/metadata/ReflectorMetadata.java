package com.em.validation.rebind.metadata;

/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

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
