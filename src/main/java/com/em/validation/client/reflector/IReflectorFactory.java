package com.em.validation.client.reflector;
/*
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

/**
 * The interface that needs to be implemented so that any class that needs
 * to can reach out and grab a backing reflector for any of the dynamic 
 * operations that they need to complete.
 * 
 * @author chris
 *
 */
public interface IReflectorFactory {

	/**
	 * Get a reflector for the given targetClass
	 * 
	 * @param <T>
	 * @param targetClass
	 * @return
	 */
	public <T> IReflector<T> getReflector(Class<? extends T> targetClass);
	
}
