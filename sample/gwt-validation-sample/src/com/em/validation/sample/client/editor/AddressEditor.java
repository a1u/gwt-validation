package com.em.validation.sample.client.editor;

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) 2011 Eminent Minds, LLC
	- Chris Ruffalo

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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.em.validation.sample.client.model.Address;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddressEditor extends Composite implements Editor<Address> {

	interface AddressEditorUiBinder extends UiBinder<Widget, AddressEditor> {}

	private static AddressEditorUiBinder uiBinder = GWT.create(AddressEditorUiBinder.class);
	
	interface Driver extends SimpleBeanEditorDriver<Address, AddressEditor> {}	
	Driver driver = GWT.create(Driver.class);
	
	private ValidatorFactory factory = null;

	@UiField
	TextBox street;
	
	@UiField
	TextBox state;
	
	@UiField
	TextBox city;
	
	@UiField
	TextBox zip;

	@UiField
	@Path("owner.firstName")
	TextBox firstName;
	
	@UiField
	@Path("owner.lastName")
	TextBox lastName;
	
	@UiField
	Button submit;
	
	@UiField
	Button clear;
	
	@UiField
	@Ignore
	HTML errors;
	
	private Address baseLineAddress = null;
	
	public AddressEditor() {
		this.factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		this.initWidget(uiBinder.createAndBindUi(this));
		this.driver.initialize(this);
	}

	public void edit(Address address) {
		this.baseLineAddress = address;
		this.driver.edit(this.baseLineAddress);
	}
	
	@UiHandler("submit")
	void submit(ClickEvent e) {
		Address address = driver.flush();
		
		Validator validator = this.factory.getValidator();

		StringBuilder builder = new StringBuilder();
		
		Set<ConstraintViolation<Address>> violations = validator.validate(address);
		
		for(ConstraintViolation<Address> violation : violations) {
			builder.append(violation.getMessage());
			builder.append(" : <i>(");
			builder.append(violation.getPropertyPath().toString());
			builder.append(" = ");
			builder.append("" + violation.getInvalidValue());
			builder.append(")</i>");
			builder.append("<br/>");
		}
		
		this.errors.setHTML(builder.toString());
		
		List<ConstraintViolation<?>> adaptedViolations = new ArrayList<ConstraintViolation<?>>();
		for(ConstraintViolation<Address> violation : violations) {
			adaptedViolations.add(violation);
		}
		
		driver.setConstraintViolations(adaptedViolations);
		
	}
	
	@UiHandler("clear") 
	public void clear(ClickEvent e){
		this.city.setText("");
		this.state.setText("");
		this.street.setText("");
		this.zip.setText("");
		this.firstName.setText("");
		this.lastName.setText("");
		this.errors.setHTML("");
	}

}
