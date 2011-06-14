package com.em.validation.client.validators;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import com.google.gwt.i18n.client.LocaleInfo;

public class MessageInterpolatorImpl implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
		LocaleInfo locale = LocaleInfo.getCurrentLocale();
		String localeName = locale.getLocaleName();
		Locale nativeLocale = new Locale(localeName);
		
		return this.interpolate(messageTemplate, context, nativeLocale);
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		
		return messageTemplate;
	}

}
