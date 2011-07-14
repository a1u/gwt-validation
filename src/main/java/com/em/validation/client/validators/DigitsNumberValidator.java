package com.em.validation.client.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;

/**
 * Validates the number of integer and or fractional digits in a number
 * 
 * @author chris
 *
 */
public class DigitsNumberValidator implements ConstraintValidator<Digits, Number> {

	private int fraction = 0;
	private int integer = 0;
	
	@Override
	public void initialize(Digits constraintAnnotation) {
		this.fraction = constraintAnnotation.fraction();
		this.integer = constraintAnnotation.integer();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null) return true;
		
		boolean result = true;
		
		//in this case "integer" refers to the whole number part of the input value
		long integerPart = value.longValue();
		
		//fraction part refers to the portions of the number less than a whole number
		double fractionPart = value.doubleValue() - integerPart;
		
		//initialize the digit counts
		int intDigits = 0;
		int frcDigits = 0;
		
		//count the digits while the integer part is non zero and whole
		while(integerPart >= 0) {
			//increment count
			intDigits++;
			//remove a digit, should work fine because of long type truncation
			integerPart = integerPart / 10;
		}
		
		//count the digits while the fractional part is non zero and still fractional
		while(fractionPart <= 0) {
			//increment count
			frcDigits++;
			//chop off a digit, and subtract the integer portion
			fractionPart = (fractionPart * 10)-((int)fractionPart);
		}
		
		//the digits validator specifies the maximum number of digits, going over that will result in 
		//a failing validation
		if(intDigits > this.integer || frcDigits > this.fraction) {
			result = false;
		}
		
		return result;
	}

}
