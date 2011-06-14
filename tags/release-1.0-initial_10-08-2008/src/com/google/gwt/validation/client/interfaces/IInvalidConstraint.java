package com.google.gwt.validation.client.interfaces;

/**
* Describe a constraint validation defect
*
* TODO add pointers to the metadata?
*
* @author Emmanuel Bernard
*/
public interface IInvalidConstraint<T> {
	/**
	* Error message
	*/
	String getMessage();
	
	/**
	* Root bean being validated
	*/ T getInvalidObject();
	
	/**
	* Bean type being validated
	*/
	//Class<?> getInvalidObjectClass();
	
	/**
	* The value failing to pass the constraint
	*/
	Object getValue();

	/**
	* the property path to the value from <code>rootBean</code>
	* Null if the value is the rootBean itself
	*/
	String getPropertyPath();

	/**
	* return the list of groups that the triggered constraint applies on and witch also are
	* within the list of groups requested for validation
	* (directly or through a group sequence)
	* TODO: considering removal, if you think it's important, speak up
	*/
	//String[] getGroups();
}

