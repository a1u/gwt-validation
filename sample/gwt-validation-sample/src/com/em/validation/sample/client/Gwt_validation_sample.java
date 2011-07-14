package com.em.validation.sample.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.sample.client.model.Address;
import com.em.validation.sample.client.model.Person;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_validation_sample implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//spi provides validator factory
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		
		//validator is provided by the factory, this validator will work on the client and server side
		final Validator validator = factory.getValidator();
		
		//get bean information
		BeanDescriptor personDescriptor = validator.getConstraintsForClass(Person.class);
		BeanDescriptor addressDescriptor = validator.getConstraintsForClass(Address.class);
		
		//create horizontal panel
		HorizontalPanel displayPanel = new HorizontalPanel();
		displayPanel.setHeight("100%");
		displayPanel.setWidth("100%");
		
		//create vertical panels
		VerticalPanel personPropertyPanel = new VerticalPanel();
		personPropertyPanel.setWidth("100%");
				
		VerticalPanel personUpdatePanel = new VerticalPanel();
		personUpdatePanel.setWidth("100%");
		
		Set<PropertyDescriptor> descriptors = new HashSet<PropertyDescriptor>();
		descriptors.addAll(personDescriptor.getConstrainedProperties());
		if(addressDescriptor != null) {
			descriptors.addAll(addressDescriptor.getConstrainedProperties());
		}
		
		//display properties
		for(PropertyDescriptor propertyDescriptor : descriptors) {
			String property = propertyDescriptor.getPropertyName();
			
			String htmlString = "<b>" + property + "</b> has the following constraints: <br/><table style=\"margin: 15px;\">";
			
			for(ConstraintDescriptor<?> constraint : propertyDescriptor.getConstraintDescriptors()) {
				
				String constraintName = constraint.getAnnotation().annotationType().getName();
				
				htmlString += "<tr><td ><i>" + constraintName + "</i> with the following attributes: <br/><table style=\"margin: 15px;\">";
				
				for(String valueKey : constraint.getAttributes().keySet()) {
					
					String value = ""+constraint.getAttributes().get(valueKey);
					
					htmlString += "<tr><td>" + valueKey + "</td><td>" + value + "</td></tr>";
					
				}
				
				if(propertyDescriptor.isCascaded()) {
					htmlString += "<tr><td>Cascaded</td><td>true</td></tr>";
				}
				
				htmlString += "</td></tr></table>";
				
			}
			
			htmlString += "</table>";
			
			personPropertyPanel.add(new HTML(htmlString));
		}

		//add side by side panels
		displayPanel.add(personUpdatePanel);
		displayPanel.add(personPropertyPanel);
		
		displayPanel.setCellWidth(personUpdatePanel, "50%");
		displayPanel.setCellWidth(personPropertyPanel, "50%");
		
		//add person property panel to root
		RootPanel.get().add(displayPanel);
		
		Label firstNameLabel = new Label("First Name: ");
		final TextBox firstNameBox = new TextBox();
		HorizontalPanel firstPanel = new HorizontalPanel();
		firstPanel.add(firstNameLabel);
		firstPanel.add(firstNameBox);
		
		Label lastNameLabel = new Label("Last Name: ");
		final TextBox lastNameBox = new TextBox();
		HorizontalPanel lastPanel = new HorizontalPanel();
		lastPanel.add(lastNameLabel);
		lastPanel.add(lastNameBox);
		
		Label streetLabel = new Label("Street Name: ");
		final TextBox streetBox = new TextBox();
		HorizontalPanel streetPanel = new HorizontalPanel();
		streetPanel.add(streetLabel);
		streetPanel.add(streetBox);
		
		Label cityLabel = new Label("City Name: ");
		final TextBox cityBox = new TextBox();
		HorizontalPanel cityPanel = new HorizontalPanel();
		cityPanel.add(cityLabel);
		cityPanel.add(cityBox);
		
		personUpdatePanel.add(firstPanel);
		personUpdatePanel.add(lastPanel);
		personUpdatePanel.add(streetPanel);
		personUpdatePanel.add(cityPanel);
		
		Button changeAndValidate = new Button();
		changeAndValidate.setText("Validate");
		
		personUpdatePanel.add(changeAndValidate);
		
		//box
		final VerticalPanel errorBox = new VerticalPanel();
		personUpdatePanel.add(errorBox);
		
		changeAndValidate.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String firstName = firstNameBox.getValue();
				String lastName = lastNameBox.getValue();
				
				Person p = new Person();
				p.setFirstName(firstName);
				p.setLastName(lastName);
				
				String streetName = streetBox.getValue();
				String cityName = cityBox.getValue();
				
				Address a = new Address();
				a.setStreet(streetName);
				a.setCity(cityName);
				
				//p.setAddress(a);
				//a.setOwner(p);
				
				Person[] people = new Person[]{
						p,
						new Person(),
						new Person()
				};
				
				a.setOwners(people);
				a.setOwnerList(Arrays.asList(people));
				
				Map<String,Person> personMap = new HashMap<String, Person>();
				
				personMap.put("primary",p);
				personMap.put("secondary", new Person());
				
				a.setPersonMap(personMap);
				
				Set<ConstraintViolation<Address>> violations = validator.validate(a);
				
				errorBox.clear();
				
				if(violations != null) {
					for(ConstraintViolation<Address> violation : violations) {
						HTML html = new HTML(violation.getPropertyPath().toString() + " -> " + violation.getMessage() + " ( invalid value: " + violation.getInvalidValue() + ")");
						errorBox.add(html);
					}
				}
			}
		});
	}
}
