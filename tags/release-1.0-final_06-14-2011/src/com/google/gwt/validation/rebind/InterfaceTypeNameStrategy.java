/**
 * 
 */
package com.google.gwt.validation.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;

/**
 * Responsible for translation between validator interface and bean it is
 * related to.
 * 
 * <p>
 * Example:
 * </p>
 * <p>
 * Let's have Person class and we don't want to bind bean class directly to
 * generator strategy. We introduce PersonValidator interface extending
 * IValidator&lt;Person&gt;.
 * </p>
 * <p>
 * Validator generation will be performed using the interface:
 * GWT.create(PersonValidator.class)
 * 
 * <br/>
 * <br/>
 * 
 * Reconstruction to the bean type must be done on the PersonValidator by
 * getting the extended IValidator interface and it's first parametrized type.
 * </p>
 * 
 * @author ladislav.gazo
 */
public class InterfaceTypeNameStrategy extends AbstractTypeStrategy {
	private Class<?> interfaceClass;
	private String suffix;
	
	public InterfaceTypeNameStrategy(Class<?> interfaceClass, String suffix) {
		super();
		this.interfaceClass = interfaceClass;
		this.suffix = suffix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.validation.rebind.TypeStrategy#getBeanTypeName(java.lang
	 * .String)
	 */
	@Override
	public String getBeanTypeName(String typeName) {
		JClassType classType = context.getTypeOracle().findType(typeName);
		String ifaceFqn = interfaceClass.getCanonicalName();
		
		for(JClassType interfaceType : classType.getImplementedInterfaces()) {
			if(interfaceType.getQualifiedSourceName().equals(ifaceFqn)) {
				if (interfaceType instanceof JParameterizedType) {
					JParameterizedType paramType = (JParameterizedType) interfaceType;
					return paramType.getTypeArgs()[0].getQualifiedSourceName();
				}		
			}
		}

		throw new RuntimeException(
				"Interface is not parametrized as expected, unable to get bean type name for type = "
						+ typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.validation.rebind.TypeStrategy#getValidatorTypeName(java
	 * .lang.String)
	 */
	@Override
	public String getValidatorTypeName(String typeName) {
		return typeName + suffix;
	}

}
