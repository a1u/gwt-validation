package ${targetPackage};

/* 
GWT Validation Framework - A JSR-303 validation framework for GWT

(c) gwt-validation contributors (http://code.google.com/p/gwt-validation/)

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