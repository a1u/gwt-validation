package com.em.validation.client.messages;

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

import java.util.HashMap;
import java.util.Map;

public abstract class MessageImpl implements IMessage {

	private Map<String, String> localeMap = new HashMap<String, String>();
	
	protected void addString(String key, String value) {
		//put key and value into locale map
		this.localeMap.put(key,value);
	}
	
	@Override
	public String getString(String key) {
		return this.localeMap.get(key);
	}	
}
