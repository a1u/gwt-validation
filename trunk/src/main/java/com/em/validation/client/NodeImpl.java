package com.em.validation.client;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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

import javax.validation.Path.Node;

public class NodeImpl extends PathImpl implements Node {
	
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
