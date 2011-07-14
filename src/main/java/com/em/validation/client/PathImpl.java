package com.em.validation.client;

import java.util.Iterator;
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
					builder.append(node.getIndex());
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
