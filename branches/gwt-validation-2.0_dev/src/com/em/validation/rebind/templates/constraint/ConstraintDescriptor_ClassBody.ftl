	public ${generatedName}() {
		<#include "ConstraintDescriptor_Constructor.ftl">
	}
	
	public List<Class<? extends ConstraintValidator<${targetAnnotation}, ?>>> getConstraintValidatorClasses() {
		<#include "ConstraintDescriptor_getConstraintValidtatorClasses.ftl">
	}
	
	public Set<Class<?>> getGroups() {
		<#include "ConstraintDescriptor_getGroups.ftl">
	}
	
	public Set<Class<? extends Payload>> getPayload() {
		<#include "ConstraintDescriptor_getPayload.ftl">
	}