	//the static annotation
	private static ${targetAnnotation} baseAnnotation = <#include "ConstraintDescriptor_AnnotationDeclaration.ftl">
	
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
	
	public static ${targetAnnotation} getAnnotationInstance() {
		<#include "ConstraintDescriptor_getAnnotationInstance.ftl">
	}
