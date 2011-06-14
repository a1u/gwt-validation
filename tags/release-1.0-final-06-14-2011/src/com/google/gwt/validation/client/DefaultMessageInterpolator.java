/**
 * 
 */
package com.google.gwt.validation.client;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.validation.client.interfaces.GWTMessageInterpolator;

/**
 * Message interpolator switching parameters of the constraint in the message
 * for real values.
 * 
 * Example: @Size has "min" and "max" arguments which user would like to see as
 * constraint boundaries. Message interpolator replaces the occurence of \{min\}
 * and \{max\} parameter placeholders in the message string (output of
 * ValidatorMessages) with the real values specified for the field.
 * 
 * @author ladislav.gazo
 */
public class DefaultMessageInterpolator implements GWTMessageInterpolator {
	@Override
	public String interpolate(String template, Map<String, String> validatorArguments) {
		String message = template;
		for (Entry<String, String> entry : validatorArguments.entrySet()) {
			message = message.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
		}
		return message;
	}
}
