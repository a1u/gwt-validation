/**
 * 
 */
package com.google.gwt.validation.rebind;

/**
 * @author ladislav.gazo
 */
public class BeanTypeNameStrategy extends AbstractTypeStrategy {

	/* (non-Javadoc)
	 * @see com.google.gwt.validation.rebind.TypeStrategy#getBeanTypeName(java.lang.String)
	 */
	@Override
	public String getBeanTypeName(String typeName) {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.validation.rebind.TypeStrategy#getValidatorTypeName(java.lang.String)
	 */
	@Override
	public String getValidatorTypeName(String typeName) {
		return typeName;
	}

}
