package com.google.gwt.validation.client.test;

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
