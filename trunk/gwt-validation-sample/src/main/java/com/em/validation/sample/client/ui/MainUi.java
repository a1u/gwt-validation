package com.em.validation.sample.client.ui;

/*
 GWT Validation Framework - A JSR-303 validation framework for GWT

 (c) 2008 gwt-validation contributors (http://code.google.com/p/gwt-validation/) 

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.em.validation.sample.client.editor.AddressEditor;
import com.em.validation.sample.client.model.Address;
import com.em.validation.sample.client.model.Person;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class MainUi extends Composite {

	interface MainUiBinder extends UiBinder<Widget, MainUi> {}
	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField
	AddressEditor addressEditor;
	
	@UiField
	Tree personProperties;
	
	@UiField
	Tree addressProperties;	
	
	public MainUi() {
		this.initWidget(uiBinder.createAndBindUi(this));
		
		Address address = new Address();
		Person person = new Person();
		address.setOwner(person);
		
		this.addressEditor.edit(address);
		
		

		this.layoutTree(Address.class, this.addressProperties);
		this.layoutTree(Person.class, this.personProperties);	
	}

	private void layoutTree(Class<?> classToDescribe, Tree baseTree) {
		//create validation factory and setup metadata for static display in trees
		ValidatorFactory factory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		Validator validator = factory.getValidator();
		
		for(PropertyDescriptor descriptor : validator.getConstraintsForClass(classToDescribe).getConstrainedProperties()) {
			TreeItem item = new TreeItem();
			item.setHTML("<b>Property: " + descriptor.getPropertyName() + "</b>");

			if(descriptor.isCascaded()) {
				TreeItem cascade = new TreeItem();
				cascade.setHTML("This property is cascaded.  (Marked with @Valid.)");
				item.addItem(cascade);
			}
			
			for(ConstraintDescriptor<?> constraint : descriptor.getConstraintDescriptors()) {
				TreeItem constraintItem = new TreeItem();
				constraintItem.setHTML("Constrained by: <i>" + constraint.getAnnotation().annotationType().getName() + "</i>");
				
				for(String attribute : constraint.getAttributes().keySet()) {
					TreeItem attributeItem = new TreeItem();
					Object value = constraint.getAttributes().get(attribute);
					if(value instanceof Class[]) {
						Class<?>[] classes = (Class<?>[])value;
						if(classes.length > 0) {
							attributeItem.setHTML(attribute);
							for(Class<?> cl : classes) {
								TreeItem classItem = new TreeItem();
								classItem.setHTML(cl.getName());
								attributeItem.addItem(classItem);
							}							
							constraintItem.addItem(attributeItem);
						}
					} else if(value instanceof Flag[]) {
						Flag[] flags = (Flag[])value;
						if(flags.length > 0) {
							attributeItem.setHTML("flags");
							for(Flag flag : flags) {
								TreeItem flagItem = new TreeItem();
								flagItem.setHTML("<i>" + flag.toString() + "</i>");
								attributeItem.addItem(flagItem);
							}
							constraintItem.addItem(attributeItem);
						}
					} else {
						attributeItem.setHTML("<i>" + attribute + "</i> = " + value);
						constraintItem.addItem(attributeItem);
					}
				}
				
				item.addItem(constraintItem);
			}			
			
			baseTree.addItem(item);
		}
	}
	
}
