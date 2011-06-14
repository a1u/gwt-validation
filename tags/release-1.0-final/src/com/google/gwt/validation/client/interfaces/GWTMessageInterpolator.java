/**
 * 
 */
package com.google.gwt.validation.client.interfaces;

import java.util.Map;

/**
 * Message interpolator on the client side using output of localized string from
 * ValidationMessages. It is responsible for processing the string e.g. for the
 * purpose of transforming optional parameters to respective values defined in
 * constraints.
 * 
 * Message interpolator is defined in ValidationConfiguration and can be defined
 * at startup.
 * 
 * @author eldzi
 */
public interface GWTMessageInterpolator {
	/**
	 * Do the interpolation specified by the implementor of the interface.
	 * 
	 * @param template
	 *            Output of localized string from ValidationMessages.
	 * @param validatorArguments
	 *            Property map of a constraint that the message is related to.
	 * @return Interpolated message as expected by user output.
	 */
	public String interpolate(String template, Map<String, String> validatorArguments);
}
