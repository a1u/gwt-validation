package com.em.validation.client.model.message;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.em.validation.client.model.constraint.CreditCard;

public class MessageExampleTestClass {

	@NotNull
	private String willBeNullViolationString = null;
	
	@Max(value=30)
	private int willBeGreaterThan30 = 31;
	
	@Size(min=5, max=15, message="Key must have \\{{min}\\} \\ \\{{max}\\} characters")
	private String willBeOutOfBounds = "1234567890abcdefghij";
	
	@Digits(integer=9, fraction=2)
	private double willHaveOutOfBoundsComponents = 12.11234564;
	
	@CreditCard(message="{myapp.creditcard.error}")
	private String badCreditCard = "123abc";

	public String getWillBeNullViolationString() {
		return willBeNullViolationString;
	}

	public void setWillBeNullViolationString(String willBeNullViolationString) {
		this.willBeNullViolationString = willBeNullViolationString;
	}

	public int getWillBeGreaterThan30() {
		return willBeGreaterThan30;
	}

	public void setWillBeGreaterThan30(int willBeGreaterThan30) {
		this.willBeGreaterThan30 = willBeGreaterThan30;
	}

	public String getWillBeOutOfBounds() {
		return willBeOutOfBounds;
	}

	public void setWillBeOutOfBounds(String willBeOutOfBounds) {
		this.willBeOutOfBounds = willBeOutOfBounds;
	}

	public double getWillHaveOutOfBoundsComponents() {
		return willHaveOutOfBoundsComponents;
	}

	public void setWillHaveOutOfBoundsComponents(double willHaveOutOfBoundsComponents) {
		this.willHaveOutOfBoundsComponents = willHaveOutOfBoundsComponents;
	}

	public String getBadCreditCard() {
		return badCreditCard;
	}

	public void setBadCreditCard(String badCreditCard) {
		this.badCreditCard = badCreditCard;
	}
	
}
