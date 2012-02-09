package com.em.validation.client;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import java.io.Serializable;

import javax.validation.Path.Node;

public class NodeImpl extends PathImpl implements Node, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		boolean inIterable = this.inIterable;
		for(Node node : this) {
			if(inIterable) break;
			inIterable = inIterable || node.isInIterable();
		}		
		return inIterable;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		if(this.getName() == null) {
			builder.append("[");
			if(this.getIndex() != null) {
				if(this.getIndex() >= 0) {
					builder.append(this.getIndex());
				}
			} else if(this.getKey() != null) {
				builder.append(this.getKey());
			}
			builder.append("]");
		} else if(this.getName() != null) {
			builder.append(this.getName());
		}		
		return builder.toString();
	}
	
	
}
