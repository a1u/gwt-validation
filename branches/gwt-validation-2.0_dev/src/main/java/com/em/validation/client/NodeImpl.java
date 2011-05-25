package com.em.validation.client;

import javax.validation.Path.Node;

public class NodeImpl implements Node {
	
	private String name = null;
	
	private Integer index = null;

	private boolean inIterable = false;
	
	private Object key = null; 

	public NodeImpl() {
		
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isInIterable() {
		return this.inIterable;
	}

	@Override
	public Integer getIndex() {
		return this.index;
	}

	@Override
	public Object getKey() {
		return this.key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setInIterable(boolean inIterable) {
		this.inIterable = inIterable;
	}

	@Override
	public boolean equals(Object obj) {
	//check basic preconditions for equality
		if(obj == null) return false;
		if(!this.getClass().equals(obj.getClass())) return false;
		
		//do specific equality checks
		NodeImpl compare = (NodeImpl)obj;
		
		//check the name (property path must be the same)
		if(!this.getName().equals(compare.getName())) return false;
		
		//check if it is iterable
		if(this.isInIterable() != compare.isInIterable()) return false;
		
		//path is inside of a map or list
		if(this.isInIterable()) {
			
			//we have an index, so compare
			if(this.getIndex() != null) {
				if(!this.getIndex().equals(compare.getIndex())) return false;
			}
			
			//we have a key, so compare
			if(this.getKey() != null) {
				if(!this.getKey().equals(compare.getKey())) return false;
			}
			
		}
		
		//none of the individual checks failed, so must be equal
		return true;
	}
	
	
}
