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
		
	String javax_validation_constraints_AssertFalse_message();
	String javax_validation_constraints_AssertTrue_message();
	String javax_validation_constraints_DecimalMax_message();
	String javax_validation_constraints_DecimalMin_message();
	String javax_validation_constraints_Digits_message();
	String javax_validation_constraints_Future_message();
	String javax_validation_constraints_Min_message();
	String javax_validation_constraints_Max_message();
	String javax_validation_constraints_NotNull_message();
	String javax_validation_constraints_Null_message();
	String javax_validation_constraints_Past_message();
	String javax_validation_constraints_Pattern_message();
	String javax_validation_constraints_Size_message();
	
//	String constraint_notNull();
//	String constraint_size();
//	String constraint_min();
//	String constraint_max();
//	String constraint_pattern();
}
