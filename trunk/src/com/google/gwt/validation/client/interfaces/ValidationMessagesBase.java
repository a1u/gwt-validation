/**
 * 
 */
package com.google.gwt.validation.client.interfaces;

import com.google.gwt.i18n.client.Constants;

/**
 * Basic validation messages specified by JSR-303 specification.
 * 
 * @author ladislav.gazo
 */
public interface ValidationMessagesBase extends Constants {
	String javax_validation_constraints_NotNull_message();
	String javax_validation_constraints_Size_message();
	String javax_validation_constraints_Min_message();
	String javax_validation_constraints_Max_message();
}
