
package com.em.validation.client.model.defects.defect_68;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {AllowedValuesValidator.class})
public @interface AllowedValues {

	Values[] value();
	String message() default "{some.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
