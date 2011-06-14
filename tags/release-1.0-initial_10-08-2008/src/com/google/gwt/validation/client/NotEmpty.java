package com.google.gwt.validation.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Asserts that the field or method is not empty when validated.  
 * <br/>
 * It also asserts that the field or method is not null.
 * <br/>
 * If this behavior seems contrary to the normal pattern of returning an invalid constraint when the 
 * value is null this is because I've had this discussion with members of the JSR-303 pannel and they've 
 * assured me that this is the intended behavior for <code>@NotEmpty</code.>
 * <br/>
 * Further it can be seen as the composition of:
 * <ul>
 * 	<li><code>@NotNull</code></li>
 * 	<li><code>@Length(minimum=1)</code></li>
 * </ul>
 *  
 * @author chris
 *
 */
@Documented
@Target({METHOD, FIELD})
@ConstraintValidator(NotEmptyValidator.class)
@Retention(RUNTIME)
public @interface NotEmpty {
	
	/**
	 * Message that is returned on validation failure.
	 * 
	 * @return
	 */
	public String message() default "{constraint.notempty}";
	
	
	/**
	 * Groups that the constraint belongs to
	 * 
	 * @return
	 */
	public String[] groups() default {};

}
