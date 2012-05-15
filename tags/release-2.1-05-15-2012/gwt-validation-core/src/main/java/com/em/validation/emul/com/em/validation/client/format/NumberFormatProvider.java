package com.em.validation.client.format;

import com.google.gwt.i18n.client.NumberFormat;

public enum NumberFormatProvider {

	INSTANCE;
	
	public double getDoubleFromString(String input) {
		 double number = 0;
		 
		 try {
			 number = NumberFormat.getDecimalFormat().parse(input);
		 } catch (NumberFormatException nfe) {
			 try {
				 number = NumberFormat.getScientificFormat().parse(input);
			 } catch (NumberFormatException nfe2) { 
				 nfe2.printStackTrace();
			 }
		 }		 
		 
		 return number;
	 }
	 
	 public long getLongFromString(String input) {
		 Double d = new Double(this.getDoubleFromString(input));
		 return d.longValue();		 
	 }	
}
