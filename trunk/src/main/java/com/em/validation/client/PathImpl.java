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
			
			if(index != 0 && node.getKey() == null && node.getIndex() == null) {
				builder.append(".");
			}
			builder.append(node.toString());
		
			index++;
		}
		
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj);
	}

}
