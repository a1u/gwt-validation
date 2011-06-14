package com.google.gwt.validation.client;


import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Groups are useful to define subsets of constraints for validation purposes. Assigning constraints to groups provides
 * the ability to do partial validation to the group. The groups element of the constraint annotation specifies the
 * groups in which the constraint participates. At validation time, an optional parameter can be specified that narrows
 * the set of groups to be validated.
 * <br/>
 * One important application for grouping is to control the order of evaluation of constraints. There are often scenarios
 * where a preliminary set of constraints should be evaluated prior to other constraints. This is useful in two scenarios:
 * <ul>
 * <li>the second group depends on a stable state to run properly</li>
 * <li>the second group is a heavy consumer of time, CPU or memory and should be avoided if possible</li>
 * </ul>
 * <br/>
 * (Taken from the JSR-303 draft)
 * 
 * @author chris
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface GroupSequence {
	String name() default "";
	String[] sequence() default {};
}