package com.em.validation.client;

import java.util.Stack;
import javax.validation.Path;
import javax.validation.Path.Node;

public class PathImpl extends Stack<Node> implements Path {

	/**
	 * Serial ID for stack version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks 
	 * 
	 * @param node
	 * @return
	 */
	public boolean cyclicCheck(Path fragment) {
		
		
		return false;
	}

}
