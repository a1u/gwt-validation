package com.google.gwt.validation.client;

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

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.interfaces.IValidatable;
import com.google.gwt.validation.client.interfaces.IValidator;

public class GwtTestCodeMuseumTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.google.gwt.validation.Validation"; 
	}
	
	@GroupSequence(name="default", sequence={"minimal","full","extended"}) 
	public class Submission implements IValidatable { 
	          @NotEmpty(groups={"minimal"}) 
	          @NotNull(groups={"minimal"}) 
	          @Length(groups={"minimal"},minimum=3) 
	          private String FullName; 
	          public String getFullName() { return this.FullName; } 
	          public void setFullName(final String inFullName) {this.FullName = inFullName;} 
	} 
	
	/**
	 * http://groups.google.com/group/gwt-validation/browse_thread/thread/7607c99111723fa9?hl=en
	 * 
	 * Jim reported this probelm and I wanted to add it to make sure that it both
	 * works and will continue to work for all versions of GWT
	 * 
	 */
	//@Test
	public void testJim_01_02_2009() {
		
		final Button clickMeButton = new Button();
		
		final TextBox txtFullName = new TextBox();
		txtFullName.setText(null);		
		
		clickMeButton.addClickListener(new ClickListener() { 
            public void onClick(final Widget sender) { 
                    final Submission mySubmission = new Submission(); 
                    mySubmission.setFullName(txtFullName.getText()); 
                    final IValidator<Submission> myvalidator = GWT.create(Submission.class); 
                    Set<InvalidConstraint<Submission>> invalidConstraints = myvalidator.validate(mySubmission,"default"); 
                    
                    //IC should be more than one if the txt is
                    //null
                    assertTrue("", invalidConstraints.size() > 0);
                    
                    //set again
                    txtFullName.setText("");
                    
                    //IC should be more than one if the txt is
                    //empty
                    mySubmission.setFullName(txtFullName.getText()); 
                    invalidConstraints = myvalidator.validate(mySubmission,"default");
                    assertTrue("", invalidConstraints.size() > 0);

                    //set again
                    txtFullName.setText("12");

                    //IC should be more than one if the txt is
                    //less than 3
                    mySubmission.setFullName(txtFullName.getText()); 
                    invalidConstraints = myvalidator.validate(mySubmission,"default");
                    assertTrue("", invalidConstraints.size() > 0);
                    
                    //set again
                    txtFullName.setText("12345678");

                    //IC should be more than 0 if the text is
                    //greater than (or equal to) 3
                    mySubmission.setFullName(txtFullName.getText()); 
                    invalidConstraints = myvalidator.validate(mySubmission,"default");
                    assertTrue("", invalidConstraints.size() == 0);
            } 
		}); 
		
		//fire click event
		clickMeButton.click();
	}
}
