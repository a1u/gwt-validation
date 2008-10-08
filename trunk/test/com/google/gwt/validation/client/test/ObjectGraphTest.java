package com.google.gwt.validation.client.test;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.Valid;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class ObjectGraphTest implements IValidatable{

	@NotNull
	@Valid
	private AnnotatedClass ac = new AnnotatedClass(); //4
	
	@NotNull
	@Valid
	private TestModel tm = new TestModel(); //6

	@Valid												//6					//6
	private TestModel[] tmArray = new TestModel[]{new TestModel(), new TestModel()};
	
	@Valid
	private List<AnnotatedClass> acList = new ArrayList<AnnotatedClass>();
	
	@Valid
	private Set<AnnotatedClass> acSet = new HashSet<AnnotatedClass>();
	
	@Valid
	private Map<String, TestModel> tmMap = new HashMap<String, TestModel>();
	
	//decoy!
	@SuppressWarnings("unused")
	private AnnotatedClass[] acArray = new AnnotatedClass[]{new AnnotatedClass(), new AnnotatedClass(), new AnnotatedClass()};
	
	//ogt construct
	public ObjectGraphTest() {
		
		//fill list
		this.acList.add(new AnnotatedClass());
		this.acList.add(new AnnotatedClass());
		this.acList.add(new AnnotatedClass()); //12
		
		//fill set
		this.acSet.add(new AnnotatedClass());
		this.acSet.add(new AnnotatedClass()); //8
		
		//fill map
		this.tmMap.put("string1", new TestModel());
		this.tmMap.put("string2", new TestModel()); //12
		
		//total 32
	}
	
	public AnnotatedClass getAc() {
		return ac;
	}

	public void setAc(AnnotatedClass ac) {
		this.ac = ac;
	}

	public TestModel getTm() {
		return tm;
	}

	public void setTm(TestModel tm) {
		this.tm = tm;
	}

	public TestModel[] getTmArray() {
		return tmArray;
	}

	public void setTmArray(TestModel[] tmArray) {
		this.tmArray = tmArray;
	}

	public List<AnnotatedClass> getAcList() {
		return acList;
	}

	public void setAcList(List<AnnotatedClass> acList) {
		this.acList = acList;
	}

	public Set<AnnotatedClass> getAcSet() {
		return acSet;
	}

	public void setAcSet(Set<AnnotatedClass> acSet) {
		this.acSet = acSet;
	}

	public Map<String, TestModel> getTmMap() {
		return tmMap;
	}

	public void setTmMap(Map<String, TestModel> tmMap) {
		this.tmMap = tmMap;
	}
	
}
