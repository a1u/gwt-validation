	public ${generatedName}() {
		<#include "ConstraintDescriptor_Constructor.ftl">
	}
	
	public List<Class<? extends ConstraintValidator<${targetAnnotation}, ?>>> getConstraintValidatorClasses() {
		<#include "ConstraintDescriptor_getConstraintValidtatorClasses.ftl">
	}