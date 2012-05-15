package com.em.validation.client.format;

import java.text.NumberFormat;
import java.text.ParseException;

public enum NumberFormatProvider {

	INSTANCE;
	
	public double getDoubleFromString(String input) {
		 NumberFormat formatter = NumberFormat.getInstance();
		 double number = 0;
		 		 
		 try {
			number = formatter.parse(input).doubleValue();
		 } catch (ParseException e) {
			e.printStackTrace();
		 }
		 
		 return number;
	 }
	 
	 public long getLongFromString(String input) {
		 NumberFormat formatter = NumberFormat.getInstance();
		 long number = 0;
		 		 
		 try {
			number = formatter.parse(input).longValue();
		 } catch (ParseException e) {
			e.printStackTrace();
		 }
		 
		 return number;
	 }
	
}
