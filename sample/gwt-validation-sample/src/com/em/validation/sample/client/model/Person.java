package com.em.validation.sample.client.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
	  @Size(min=1)
	  @NotNull
	  private String lastName;

	  @NotNull
	  @Size(min=3)
	  private String firstName;

	  public String getLastName() { return this.lastName; }
	  public String getFirstName() { return this.firstName; }
	  public void setLastName(String lastName) { this.lastName = lastName; }
	  public void setFirstName(String firstName) { this.firstName = firstName; }

	  @Pattern(regexp="(.*), (.*)")
	  public String getFullName() {
	     return this.lastName + ", " + this.firstName;
	  }
}
