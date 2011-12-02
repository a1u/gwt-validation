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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import javax.validation.Path;
import javax.validation.Path.Node;

public class PathImpl extends Stack<Node> implements Path {

	/**
	 * Serial ID for stack version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks for possible cyclic paths.
	 * 
	 * @param node
	 * @return
	 */
	public boolean cyclicCheck(Path fragment) {
		Set<String> nodes = new HashSet<String>();
		for(Node node : fragment) {
			if(node.getName() == null || node.getName().isEmpty()) continue;
			String key = node.getName() + ":" + node.getIndex() + ":" + node.getKey();
			if(nodes.contains(key)) {
				return true;
			}
			nodes.add(key);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Node> it = this.iterator();
		
		int index = 0; 
		
		while(it.hasNext()) {
			Node node = it.next();
			
			if(node.isInIterable()) {
				if(node.getKey() != null) {
					builder.append("{");
					builder.append(node.getKey());
					builder.append("}");
				} else if(node.getIndex() != null) {
					builder.append("[");
					if(node.getIndex() != -1) {
						builder.append(node.getIndex());
					}
					builder.append("]");
				}
			} else {
				if(index != 0) {
					builder.append(".");
				}
				builder.append(node.getName());
			}
			
			index++;
		}
		
		return builder.toString();
	}
}
