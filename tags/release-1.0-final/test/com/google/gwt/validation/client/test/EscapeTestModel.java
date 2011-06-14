package com.google.gwt.validation.client.test;

/*
GWT-Validation Framework - Annotation based validation for the GWT Framework

Copyright (C) 2008  Christopher Ruffalo

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * Test's if ValidatorGenerator properly escapes it's string literals
 * @author nosnevelxela@gmail.com (Alex Levenson)
 */

import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.Pattern;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class EscapeTestModel implements IValidatable {
  
  @Min(minimum=48,message="Must be at least 48\" to ride, and a \u2603 as well.")
  private int height = 40;
  
  @Pattern(pattern=".*\\s.*")
  private String hasWhiteSpace = "NoWhiteSpaceHere";
  
  @Pattern(pattern=".*\\s\u2603\\s.*")
  private String snowManAndWhiteSpace = "NoSnowMenOrWhiteSpaceHere";

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getHasWhiteSpace() {
    return hasWhiteSpace;
  }

  public void setHasWhiteSpace(String hasWhiteSpace) {
    this.hasWhiteSpace = hasWhiteSpace;
  }

  public String getSnowManAndWhiteSpace() {
    return snowManAndWhiteSpace;
  }

  public void setSnowManAndWhiteSpace(String snowManAndWhiteSpace) {
    this.snowManAndWhiteSpace = snowManAndWhiteSpace;
  }
  
  
}
