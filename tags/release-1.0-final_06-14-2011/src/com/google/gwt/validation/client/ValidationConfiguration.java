/**
 * 
 */
package com.google.gwt.validation.client;

import com.google.gwt.validation.client.interfaces.GWTMessageInterpolator;

/**
 * Central configuration for the validation modifiable at startup of the
 * application. There are always default values so there is no need to call
 * init() method.
 * 
 * @author ladislav.gazo
 */
public class ValidationConfiguration {
	private static boolean initialized = false;
	private static GWTMessageInterpolator messageInterpolator;

	/**
	 * Initializes correct configuration values. Can be called only once.
	 * 
	 * @param messageInterpolator
	 */
	public static void init(GWTMessageInterpolator messageInterpolator) {
		if (initialized) {
			throw new RuntimeException("Already initialized");
		}

		ValidationConfiguration.messageInterpolator = messageInterpolator;
	}

	/**
	 * @return Message interpolator used for transforming non-interpolated
	 *         messages to user-friendly ones.
	 */
	public static GWTMessageInterpolator getMessageInterpolator() {
		if (messageInterpolator == null) {
			messageInterpolator = new DefaultMessageInterpolator();
		}
		return messageInterpolator;
	}
}
