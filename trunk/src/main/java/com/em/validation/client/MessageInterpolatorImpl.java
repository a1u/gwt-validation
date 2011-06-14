package com.em.validation.client;

import java.util.Locale;

import javax.validation.MessageInterpolator;

public class MessageInterpolatorImpl implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
		
		return this.interpolate(messageTemplate, context, null);
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		
		return messageTemplate;
	}

}
