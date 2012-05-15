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
	
	/**
	 * The object key for hash type implementations.  Transient because GWT cannot
	 * serialize raw objects.
	 */
	private transient Object key = null; 

	public NodeImpl() {
		
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isInIterable() {
		boolean localInIterable = this.inIterable;
		for(Node node : this) {
			if(localInIterable) {
				break;
			}
			localInIterable = localInIterable || node.isInIterable();
		}		
		return localInIterable;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (inIterable ? 1231 : 1237);
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof NodeImpl)) {
			return false;
		}
		NodeImpl other = (NodeImpl) obj;
		if (inIterable != other.inIterable) {
			return false;
		}
		if (index == null) {
			if (other.index != null) {
				return false;
			}
		} else if (!index.equals(other.index)) {
			return false;
		}
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
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
