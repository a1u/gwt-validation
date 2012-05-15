package ${targetPackage};

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

//generic imports
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;
import com.em.validation.client.metadata.ConstraintDescriptorImpl;
import com.em.validation.client.generated.factory.AnnotationInstanceFactory;

//the target constraint annotation
import ${annotationImportName};

public enum ${generatedName} implements ConstraintDescriptor<${targetAnnotation}> {

	INSTANCE;

	private ConstraintDescriptorImpl<${targetAnnotation}> instance = null;
	
	private ${generatedName}() {
		this.instance = new ConstraintDescriptorImpl<${targetAnnotation}>() {
			public void init() {
				//create underlying annotation
				this.annotation = AnnotationInstanceFactory.INSTANCE.getAnnotationFactory(${targetAnnotation}.class).getAnnotation("${hash(signature)}");
		
				//get property map
				this.propertyMap = AnnotationInstanceFactory.INSTANCE.getAnnotationFactory(${targetAnnotation}.class).getPropertyMap("${hash(signature)}");
			
				//save composed constraints
				<#list composedOf as composed>
				this.composedOf.add(${composed.className}.INSTANCE);
				</#list>
				
				//report as single violation
				<#if reportAsSingleViolation != "null">
				this.reportAsSingleViolation = ${reportAsSingleViolation};
				</#if>

				//add classes that validate this constraint				
				<#list validatedBy as validator>
				validatedBy.add(${validator}.class);
				</#list>				
			}
		};
	} 
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.instance.getAttributes();
	}
	
	@Override
	public Set<ConstraintDescriptor<?>> getComposingConstraints() {
		return this.instance.getComposingConstraints();
	}
	
	@Override
	public boolean isReportAsSingleViolation() {
		return this.instance.isReportAsSingleViolation();
	}
	
	@Override
	public Set<Class<?>> getGroups() {
		return this.instance.getGroups();
	}

	@Override
	public Set<Class<? extends Payload>> getPayload() {
		return this.instance.getPayload();
	}
	
	@Override
	public ${targetAnnotation} getAnnotation() {
		return this.instance.getAnnotation();
	}

	@Override
	public List<Class<? extends ConstraintValidator<${targetAnnotation}, ?>>> getConstraintValidatorClasses() {
		return this.instance.getConstraintValidatorClasses();
	}
	
}